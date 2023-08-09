package com.fa.sonagi.jwt;

import static com.fa.sonagi.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fa.sonagi.oauth.utils.CookieUtil;
import com.fa.sonagi.user.entity.Users;
import com.fa.sonagi.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TYPE = "Bearer";
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate<String, String> redisTemplate;
	private final UserRepository userRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 1. Request Header 에서 JWT 토큰 추출 - token null => 로그아웃
		String token = resolveToken((HttpServletRequest)request);
		if (token == null) {
			chain.doFilter(request, response);
			return;
		}
		// 2. validateToken 으로 토큰 유효성 검사
		String validateResult = jwtTokenProvider.validateToken(token);
		if (Objects.equals(validateResult, "vaild")) {
			// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder
				.getContext()
				.setAuthentication(authentication);
			chain.doFilter(request, response);
			return;
		}
		//3. expired 된 access 토큰 처리
		if (Objects.equals(validateResult, "isExpired")) {
			Optional<String> cookie = CookieUtil
				.getCookie((HttpServletRequest)request, REFRESH_TOKEN)
				.map(Cookie::getValue);

			// 쿠키에 리프레시 토큰이 없음. => 로그아웃
			if (cookie.isEmpty()) {
				chain.doFilter(request, response);
				return;
			}

			// refreshToken 만료 => 로그아웃
			String refreshTokenFromCookie = cookie.get();
			if (jwtTokenProvider.getIsExipired(refreshTokenFromCookie)) {
				chain.doFilter(request, response);
				return;
			}

			String userId = jwtTokenProvider
				.parseClaims(refreshTokenFromCookie)
				.getSubject();

			String refreshTokenFromRedis = redisTemplate
				.opsForValue()
				.get("RT" + userId);

			// Redis 에 토큰이 없음 => 로그아웃
			if (refreshTokenFromRedis == null) {
				chain.doFilter(request, response);
				return;
			}
			// redis토큰 != cookie토큰 => 로그아웃 + 토큰 burn
			if (!Objects.equals(refreshTokenFromRedis, refreshTokenFromCookie)) {
				redisTemplate
					.opsForValue()
					.getOperations()
					.delete(refreshTokenFromRedis);
			} else {// 정상 유저 - 토큰 재발급 해줘야함
				// 토큰 생성.
				Users user = userRepository
					.findById(Long.valueOf(userId))
					.orElseThrow();
				Token tokenInfo = jwtTokenProvider.createToken(userId, user.getSocialId(), user
					.getRoles()
					.get(0));
				// from Redis 기존 토큰 burn 그리고 새로 생성후 cookie 에 추가
				redisTemplate
					.opsForValue()
					.getOperations()
					.delete(refreshTokenFromRedis);
				redisTemplate
					.opsForValue()
					.set("RT" + userId, tokenInfo.getRefreshToken(), tokenInfo.getExpireTime(), TimeUnit.MILLISECONDS);
				// refresh Token -> Http only 쿠키.
				CookieUtil.deleteCookie((HttpServletRequest)request, (HttpServletResponse)response, REFRESH_TOKEN);
				CookieUtil.addCookie((HttpServletResponse)response, REFRESH_TOKEN, tokenInfo.getRefreshToken(), JwtTokenProvider.getRefreshTokenExpireTimeCookie());
				((HttpServletResponse)response).setHeader("AccessToken", tokenInfo.getAccessToken());
			}
			chain.doFilter(request, response);
		}
	}

	// Request Header 에서 토큰 정보 추출
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
