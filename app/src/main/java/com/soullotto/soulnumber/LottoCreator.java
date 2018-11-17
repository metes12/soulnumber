package com.soullotto.soulnumber;

import android.support.v4.math.MathUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LottoCreator extends SoulNumberAbstract {

    public LottoCreator(int birthday) {
        super(birthday);
    }

    @Override
    //19880710 이 들어온다고 가정하고.. 로직 수정
    public int getSoulNumber() {
        int dummyBirthDay = 19990929;
        List<Integer> soulNumber = new ArrayList<Integer>();
        List<Integer> soulNumber45 = new ArrayList<Integer>();
        int digit = 0;
        int sum = 0;

        //birthday split
        while (dummyBirthDay > 0) {
            digit = dummyBirthDay % 10;
            dummyBirthDay /= 10;
            soulNumber.add(digit);
        }

        //digit sum
        for(int i = 0; i < soulNumber.size();i++) {
            sum += soulNumber.get(i);
        }

        // 45 over
        if (sum > 45) {
            while(sum > 0) {
                digit = sum % 10;
                sum /= 10;
                soulNumber45.add(digit);
            }
            for(int j = 0; j < soulNumber45.size(); j++) {
                sum += soulNumber45.get(j);
            }
        }
        return sum;
    }

    @Override
    public int[] getLottoNumberArray() {
        Random r = new Random();
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
        Random r = new Random();
        int randomNumber;
        HashSet<Integer> set = new HashSet<>();

        for(int includeIndex = 0; includeIndex < includeArray.length; includeIndex++) {
            set.add(includeArray[includeIndex]);
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

}
