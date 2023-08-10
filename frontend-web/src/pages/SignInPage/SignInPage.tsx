import { Background } from '@/components/atoms/Background/Background.styles';
import orangeBackground from '@/assets/images/background-orange-to-blue.png';
import { Text } from '@/components/atoms/Text/Text.styles';
import family from '@/assets/images/img-family.png';
import { Image } from '@/components/atoms/Image/Image';
import * as S from '@/pages/SignInPage/SignInPage.style';
import RegisterField from '@/components/molecules/RegisterField/RegisterField';
import { useRecoilState } from 'recoil';
import { userInfoState } from '@/states/UserState';
import { produce } from 'immer';
import { useNavigate } from 'react-router-dom';
import { PATH } from '@/constants/path';
import { useUpdateUser } from '@/apis/User/Mutations/useUpdateUser';

const SignInPage = () => {
  const navigate = useNavigate();
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const updateUserMutation = useUpdateUser();

  const placeholder = '이름을 입력하세요';
  const alertMessage = '10자 이내로 입력해주세요';

  const onClickButtonAction = (value: string) => {
    updateUserMutation.mutate({
      userId: userInfo.userId,
      name: value,
    });

    setUserInfo(
      produce(draft => {
        draft['name'] = value;
      })
    );

    navigate(PATH.REGISTER);
  };

  return (
    <Background $background={orangeBackground}>
      <S.SignInPageContainer>
        <S.SignInPageWrapper>
          <Image src={family} width={12} />
          <Text color={'black3'}>
            환영합니다!
            <br />
            양육자의 이름을 입력해주세요
          </Text>
          <RegisterField
            onClickButtonAction={onClickButtonAction}
            option="default"
            size="medium"
            placeholder={placeholder}
            alertMessage={alertMessage}
          />
        </S.SignInPageWrapper>
      </S.SignInPageContainer>
    </Background>
  );
};

export default SignInPage;
