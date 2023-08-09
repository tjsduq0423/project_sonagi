import { useQuery } from '@tanstack/react-query';
import { getBaby } from '@/apis/Baby/babyAPI';
import { useSetRecoilState } from 'recoil';
import { babiesOfUserState } from '@/states/BabyState';
import { BabiesOfUser } from '@/types';

const useGetBaby = (userId: number) => {
  const setBabyInfo = useSetRecoilState(babiesOfUserState);
  return useQuery(['baby', userId], () => getBaby(userId), {
    onSuccess: (data: BabiesOfUser[]) => {
      setBabyInfo(data);
      return data;
    },
    onError: (err: Error) => {
      console.log('Error fetching baby data:', err.message);
    },
    suspense: true,
  });
};

export { useGetBaby };
