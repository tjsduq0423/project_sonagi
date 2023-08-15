import Button from '@/components/atoms/Button/Button';
import { Image } from '@/components/atoms/Image/Image';
import { Text } from '@/components/atoms/Text/Text.styles';
import * as S from '@/components/organisms/OurBabyInfo/OurBabyInfo.styles';
import setting from '@/assets/images/icon-setting-grey.png';
import babyBlue from '@/assets/images/img-baby-blue.png';
import babyYellow from '@/assets/images/img-baby-yellow.png';
import babyVaccine from '@/assets/images/img-vaccine.png';
import babyCard from '@/assets/images/img-baby-card.png';
import BabyPersonalInfoContainer from '@/components/organisms/BabyPersonalInfoContainer/BabyPersonalInfoContainer';
import { Background } from '@/components/atoms/Background/Background.styles';
import BackgroundImg from '@/assets/images/background.png';
import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { BabiesOfUser, Baby, User } from '@/types';
import { babyInfoState, selectedBabyState } from '@/states/babyState';
import { Outlet, useNavigate } from 'react-router-dom';
import { PATH } from '@/constants/path';
import { BabyCodeModal } from '@/components/organisms/BabyCodeModal/BabyCodeModal';
import { Container } from '@/pages/MyPagePage/MyPagePage.styles';
import { useGetBabyDetail } from '@/apis/Baby/Queries/useGetBabyDetail';
import { userInfoState } from '@/states/userState';

const OurBabyInfo = () => {
  const navigate = useNavigate();

  const babyInfo: BabiesOfUser = useRecoilValue(selectedBabyState);
  const userInfo: User = useRecoilValue(userInfoState);
  const [isMale, setIsMale] = useState(babyInfo.gender === 'M');
  const [selectedBirthDate, setSelectedBirthDate] = useState('string');

  const [BabyCodeModalOpen, setBabyCodeModalOpen] = useState(false);

  const modalClose = (
    setState: React.Dispatch<React.SetStateAction<boolean>>
  ) => {
    setState(false);
  };

  console.log(userInfo.userId, babyInfo.babyId);
  useGetBabyDetail(babyInfo.babyId, userInfo.userId);

  useEffect(() => {});

  // TODO: 함께한 시간 계산하기.. 근데 selectBaby에는 date가 없다
  return (
    <S.OurBabyInfoWholeContainer>
      <S.EmptyContainer className="scrollable">
        <Background $background={BackgroundImg}>
          <BabyCodeModal
            onModalClose={() => modalClose(setBabyCodeModalOpen)}
            modalOpen={BabyCodeModalOpen}
          />
          <S.InfoEditWrapper>
            <Button
              option="default"
              size="xSmall"
              onClick={() => {
                navigate(PATH.UPDATEBABYPROFILE);
              }}
            >
              <Image src={setting} width={1} style={{ marginRight: '10px' }} />
              <Text size="small">정보 수정</Text>
            </Button>
          </S.InfoEditWrapper>
          <S.OurBabyInfoContainer>
            <S.BabyNameWrapper>
              <Text size="headMedium" $fontWeight={700}>
                {babyInfo.name}
              </Text>
            </S.BabyNameWrapper>
            <div style={{ height: '144px' }}>
              <Image
                src={isMale ? babyBlue : babyYellow}
                height={100}
                $unit="%"
              />
            </div>
            <Text size="medium1"> 함께한 시간 247일</Text>
            <Text size="headMedium" $fontWeight={700}>
              35주
            </Text>
            <S.UpperButtonContainer>
              <Button option="imgBtn">
                <S.UpperButtonWrapper
                  onClick={() => navigate(PATH.MEDICALINFO)}
                >
                  <Text size="medium1">예방접종/검진</Text>
                  <Image src={babyVaccine} width={33} $unit="px" />
                </S.UpperButtonWrapper>
              </Button>
              <Button option="imgBtn">
                <S.UpperButtonWrapper
                  onClick={() => setBabyCodeModalOpen(true)}
                >
                  <Text size="medium1">초대 코드 공유</Text>
                  <Image src={babyCard} width={33} $unit="px" />
                </S.UpperButtonWrapper>
              </Button>
            </S.UpperButtonContainer>
            <S.ButtonsDivider />
            <BabyPersonalInfoContainer
              isDisease={true}
            ></BabyPersonalInfoContainer>
            <BabyPersonalInfoContainer
              isDisease={false}
            ></BabyPersonalInfoContainer>
          </S.OurBabyInfoContainer>
        </Background>
      </S.EmptyContainer>
    </S.OurBabyInfoWholeContainer>
  );
};

export default OurBabyInfo;
