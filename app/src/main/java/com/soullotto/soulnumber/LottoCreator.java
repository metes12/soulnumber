package com.soullotto.soulnumber;

public class LottoCreator extends SoulNumberAbstract {

    public LottoCreator(int birthDay) {
        super(birthDay);
    }

    @Override
    //19880710 이 들어온다고 가정하고.. 로직 수정
    public int getSoulNumber() {
        int dummyBirthDay = 19880710;

        return 0;
    }

    @Override
    public int[] getLottoNumberArray() {
        return new int[0];
    }

    @Override
    //getSoulNumber를 이용해서 소울넘버가 포함된 로또 배열 6개 리턴
    public int[] getLottoNumberArrayWithSoulNumber() {
        return new int[0];
    }
}
