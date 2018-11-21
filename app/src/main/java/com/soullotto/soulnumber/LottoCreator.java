package com.soullotto.soulnumber;

import android.app.Activity;
import android.os.SystemClock;

import com.soullotto.utils.SoulNumberHelper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LottoCreator extends SoulNumberAbstract {

    public LottoCreator(int birthday) {
        super(birthday);
    }

    @Override
    //19880710 이 들어온다고 가정하고.. 로직 수정
    public int getSoulNumber() {
        List<Integer> soulNumber = new ArrayList<Integer>();
        List<Integer> soulNumber45 = new ArrayList<Integer>();
        int digit = 0;
        int sum = 0;

        //birthday split
        while (birthDay > 0) {
            digit = birthDay % 10;
            birthDay /= 10;
            soulNumber.add(digit);
        }

        //digit sum
        for (int i = 0; i < soulNumber.size(); i++) {
            sum += soulNumber.get(i);
        }

        // 45 over
        if (sum > 45) {
            while (sum > 0) {
                digit = sum % 10;
                sum /= 10;
                soulNumber45.add(digit);
            }
            for (int j = 0; j < soulNumber45.size(); j++) {
                sum += soulNumber45.get(j);
            }
        }
        return sum;
    }

    @Override
    public int[] getLottoNumberArray() {
        SecureRandom r = new SecureRandom();
        int randomNumber;
        HashSet<Integer> set = new HashSet<>();

        while (set.size() < 6) {
            randomNumber = (r.nextInt(44) + 1);
            set.add(randomNumber);
        }

        int[] lottoArray = new int[6];
        Iterator<Integer> iterator = set.iterator();
        for (int index = 0; index < lottoArray.length; index++) {
            lottoArray[index] = iterator.next();
        }

        Arrays.sort(lottoArray);
        return lottoArray;
    }

    private void myLottoArrayMethod() {
        //        //랜덤 추출
//        int[] LottoNumberArray = new int[6];
//        for (int i= 0; i <= 5; i++) {
//            LottoNumberArray[i] = r.nextInt(44) + 1;
//
//            //중복제거
//            for (int j = 0; j < i; j++) {
//                if (LottoNumberArray[i] == LottoNumberArray[j]) {
//                    i--;
//                }
//            }
//        }
    }

    //getIncludeExcept를 이용해서 소울넘버가 포함된 로또 배열 6개 리턴
    @Override
    public int[] getIncludeExcept(int[] exceptArray, int[] includeArray) {
        SecureRandom r = new SecureRandom();
        int randomNumber;
        HashSet<Integer> set = new HashSet<>();

        for (int includeIndex = 0; includeIndex < includeArray.length; includeIndex++) {
            set.add(includeArray[includeIndex]);
            SystemClock.sleep(r.nextInt(20));
        }

        while (set.size() < 6) {
            randomNumber = (r.nextInt(44) + 1);

            boolean isExceptNumber = false;
            for (int exceptIndex = 0; exceptIndex < exceptArray.length; exceptIndex++) {
                if (randomNumber == exceptArray[exceptIndex]) {
                    isExceptNumber = true;
                }
            }

            if (!isExceptNumber) {
                set.add(randomNumber);
                SystemClock.sleep(r.nextInt(20));
            }
        }

        int[] lottoArray = new int[6];
        Iterator<Integer> iterator = set.iterator();
        for (int index = 0; index < lottoArray.length; index++) {
            lottoArray[index] = iterator.next();
        }

        Arrays.sort(lottoArray);
        return lottoArray;
    }

    @Override
    public int getTodayNumber(Activity activity) {
        int todayNumber;
        SecureRandom r = new SecureRandom();
        todayNumber = (r.nextInt(44) + 1);

        boolean isNewDay = SoulNumberHelper.isNewDay(activity, new Date());

        if (isNewDay) {
            SoulNumberHelper.saveTodayNumber(activity, todayNumber);
            return todayNumber;
        } else {
            return SoulNumberHelper.getTodayNumber(activity);
        }
    }
}





