import React, { useEffect, useState } from 'react';
import WeekendCalendarToken from '../../molecules/WeekendCalendar/WeekendCalendarToken';
import getWeekendDate from '../../../utils/weekendUtils';
import WeekendCalendarContainer from './WeekendCalendar.styles';
import dayjs, { Dayjs } from 'dayjs';

//TODO: isRecoredDay 데이터 바인딩 이후 처리용 함수 제작.

interface WeekendCalendarProps {
  selectedDate: Date;
  onDateChange: (date: Date) => void;
}

type StatusOfDay = 'beforeToday' | 'isToday' | 'afterToday';

const WeekendCalendar: React.FC<WeekendCalendarProps> = ({
  selectedDate,
  onDateChange,
}) => {
  const [weekendDateList, setWeekendDateList] = useState<
    Array<{ date: Dayjs; day: string }>
  >([]);

  useEffect(() => {
    setWeekendDateList(getWeekendDate(selectedDate));
  }, [selectedDate]);

  const jStatusOfDay = (date: Dayjs): StatusOfDay => {
    if (date.isAfter(dayjs())) {
      return 'afterToday';
    }
    return 'beforeToday';
  };

  return (
    <>
      <WeekendCalendarContainer>
        {weekendDateList.map((dateMap, index) => (
          <WeekendCalendarToken
            key={index}
            dayNumber={dateMap['date']}
            dayOfWeek={dateMap['day']}
            statusOfDay={
              dateMap['date'].isSame(dayjs(selectedDate))
                ? 'isToday'
                : jStatusOfDay(dateMap['date'])
            }
            isRecoredDay={true}
            onClick={() => onDateChange(dateMap['date'].toDate())} // 클릭 핸들러 추가
          />
        ))}
      </WeekendCalendarContainer>
    </>
  );
};

export default WeekendCalendar;
