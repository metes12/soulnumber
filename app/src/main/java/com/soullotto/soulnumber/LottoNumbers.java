package com.soullotto.soulnumber;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

public class LottoNumbers {

    //안드로이드에서 SparseArray 사용을 권장하지만 경고 무시
    @SuppressLint("UseSparseArrays")
    private Map<Integer, int[]> lottoMap = new HashMap<>();

    public LottoNumbers() {}

    private int index = 0;
    public void add(int[] lotto) {
        lottoMap.put(index++, lotto);
    }

    public int[] getLottoMap(int index) {
        return lottoMap.get(index);
    }

    public int getSize() {
        return lottoMap.size();
    }
}
