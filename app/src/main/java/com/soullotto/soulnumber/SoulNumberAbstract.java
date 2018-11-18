package com.soullotto.soulnumber;

import android.app.Activity;

import java.util.Arrays;

public abstract class SoulNumberAbstract {

    int birthDay;

    public SoulNumberAbstract(int birthDay) {
        this.birthDay = birthDay;
    }

    //birthDay를 사용해서 소울넘버 생성 및 리턴
    public abstract int getSoulNumber();

    //new Random() 클래스를 사용하여 45까지 숫자 6개 배열을 생성하여 리턴
    public abstract int[] getLottoNumberArray();

    //new Random() 클래스를 사용하여 45까지 숫자 6개 배열을 생성하여 리턴
    //입력된 숫자를 포함하거나 제외한다.
    public abstract int[] getIncludeExcept(int[] exceptArray, int[] includeArray);

    public abstract int getTodayNumber(Activity activity);

}
