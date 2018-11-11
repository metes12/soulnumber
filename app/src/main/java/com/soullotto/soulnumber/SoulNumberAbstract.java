package com.soullotto.soulnumber;

public abstract class SoulNumberAbstract {

    private int birthDay;

    public SoulNumberAbstract(int birthDay) {
        this.birthDay = birthDay;
    }

    //birthDay를 사용해서 소울넘버 생성 및 리턴
    public abstract int getSoulNumber();

    //new Random() 클래스를 사용하여 45까지 숫자 6개 배열을 생성하여 리턴
    public abstract int[] getLottoNumberArray();

    //new Random() 클래스를 사용하여 45까지 숫자 6개 배열을 생성하여 리턴
    //단, 소울넘버 1개를 포함한다.
    public abstract int[] getLottoNumberArrayWithSoulNumber();

}
