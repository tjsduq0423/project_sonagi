import CalendarBar from '@/components/molecules/CalendarBar/CalendarBar';
import WeekendCalendar from '../../components/organisms/WeekendCalendar/WeekendCalendar';
import btnAddDiary from '@/assets/images/btn-add-diary.png';
import { DiaryListContainer } from '@/pages/DiaryPage/DiaryPage.styles';
import { Image } from '@/components/atoms/Image/Image';
import { useNavigate } from 'react-router-dom';
import { PATH } from '@/constants/path';
import { useRecoilState, useRecoilValue } from 'recoil';
import { selectedBabyState } from '@/states/babyState';
import DiaryCard from '@/components/organisms/DiaryCard/DiaryCard';
import { BabiesOfUser } from '@/types';
import { selectedDateState } from '@/states/dateState';
import { useGetDiaryInfoByBabyId } from '@/apis/Diary/Queries/useGetDiaryInfoByBabyId';
import { diaryRecordList, writtenDiaryDateList } from '@/states/diaryState';
import { useGetAllDiaryRecordDates } from '@/apis/Diary/Queries/useGetAllDiaryRecordDates';

const DiaryPage: React.FC = () => {
  const navigate = useNavigate();
  const babyInfo: BabiesOfUser = useRecoilValue(selectedBabyState);
  const selectedDate: string = useRecoilValue(selectedDateState);
  const [diaries, setDiaries] = useRecoilState(diaryRecordList);
  const recordedDateList: string[] = useRecoilValue(writtenDiaryDateList);

  // 그냥 호출만 해놓으면 babyInfo 의 값이 변화함에 따라 호출이 변하는가?
  useGetDiaryInfoByBabyId(babyInfo.babyId, selectedDate);
  useGetAllDiaryRecordDates(babyInfo.babyId);
  return (
    <>
      <CalendarBar></CalendarBar>
      <WeekendCalendar></WeekendCalendar>
      <DiaryListContainer>
        {diaries.length > 0 &&
          diaries.map((diary, index) => (
            <DiaryCard
              key={index}
              diaryId={diary.diaryId}
              writer={diary.userName}
              writenDate={diary.writedTime}
              content={diary.content}
              imgUrls={diary.imgUrls}
            ></DiaryCard>
          ))}
        <Image
          src={btnAddDiary}
          width={100}
          $unit="%"
          onClick={() => navigate(PATH.DIARYREGISTER)}
        ></Image>
      </DiaryListContainer>
    </>
  );
};

export default DiaryPage;
