package com.soullotto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.android.gms.ads.MobileAds;
import com.soullotto.commons.Constants;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.ColorBall;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.BallHelper;
import com.soullotto.utils.SoulNumberHelper;

import java.util.ArrayList;
import java.util.List;

public class SoulNumberActivity extends Activity {

    private static final String ADMOB_ID = "ca-app-pub-1611757228618027~9373727028";
    private static final String ADMOB_REWARD_ID = "ca-app-pub-1611757228618027/6901855127";

    public static final int MS_SHAKE_TIME = 4000;

    public static final int REQ_CODE_SELECT_NUMBER_INCLUDE = 1;
    public static final int REQ_CODE_SELECT_NUMBER_EXCEPT = 2;

    private LottoCreator lottoCreator;
    private Button imgvSoul;
    private Button imgvToday;
    private ImageView btnLotto;
    private LinearLayout imgvShake;
    private LinearLayout linearLayoutNumbers;
    private LinearLayout linearLayoutExcept;
    private LinearLayout linearLayoutInclude;
    private GridLayout gridLotto;

    private List<Integer> exceptList = new ArrayList<>();
    private List<Integer> includeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soul_number);

        initializeAdmob();

        lottoCreator = new LottoCreator(SoulNumberHelper.getBirthDay(getApplicationContext()));

        imgvSoul = findViewById(R.id.imgv_soul);
        imgvToday = findViewById(R.id.imgv_today);
        imgvShake = findViewById(R.id.linearv_shake);
        btnLotto = findViewById(R.id.btn_get_lotto);

        linearLayoutExcept = findViewById(R.id.linearv_except);
        linearLayoutInclude = findViewById(R.id.linearv_include);

        gridLotto = findViewById(R.id.gridv_lotto);

        linearLayoutNumbers = findViewById(R.id.linerv_numbers);
        imgvSoul.setText(String.valueOf(lottoCreator.getSoulNumber()));
        imgvToday.setText(String.valueOf(lottoCreator.getTodayNumber(this)));

        BallHelper.makeColorBall(imgvSoul);
        BallHelper.makeColorBall(imgvToday);
    }

    private void initializeAdmob() {
        MobileAds.initialize(this, ADMOB_ID);
    }

    public void onIncludeButtonClicked(View view) {
        Intent intent = new Intent(this, LottoSelectAcivity.class);
        intent.putExtra(LottoSelectAcivity.KEY_TYPE, LottoSelectAcivity.TYPE_INCLUDE);
        startActivityForResult(intent, REQ_CODE_SELECT_NUMBER_INCLUDE);
    }

    public void onExceptButtonClicked(View view) {
        Intent intent = new Intent(this, LottoSelectAcivity.class);
        intent.putExtra(LottoSelectAcivity.KEY_TYPE, LottoSelectAcivity.TYPE_EXCEPT);
        startActivityForResult(intent, REQ_CODE_SELECT_NUMBER_EXCEPT);
    }

    public void onLottoButtonClicked(View view) {
        btnLotto.setVisibility(View.GONE);
        imgvShake.setVisibility(View.VISIBLE);
        linearLayoutNumbers.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(MS_SHAKE_TIME);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnLotto.setVisibility(View.VISIBLE);
                        imgvShake.setVisibility(View.GONE);
                        linearLayoutNumbers.setVisibility(View.VISIBLE);

                        int [] exceptArray = BallHelper.convertIntegers(exceptList);
                        int [] includeArray = BallHelper.convertIntegers(includeList);

                        gridLotto.removeAllViews();

                        for (int i = 0; i < 5; i++) {
                            int[] lotto = lottoCreator.getIncludeExcept(exceptArray, includeArray);

                            for (int j = 0; j < lotto.length; j++) {
                                gridLotto.addView(BallHelper.makeNumberBall(SoulNumberActivity.this, lotto[j]));
                            }
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SELECT_NUMBER_INCLUDE) {
            includeList = data.getIntegerArrayListExtra(LottoSelectAcivity.KEY_SELECTED);

            linearLayoutInclude.removeAllViews();
            for (int index = 0; index < includeList.size(); index++) {
                linearLayoutInclude.addView(BallHelper.makeNumberBall(this, includeList.get(index)));
            }
        } else if (requestCode == REQ_CODE_SELECT_NUMBER_EXCEPT) {
            exceptList = data.getIntegerArrayListExtra(LottoSelectAcivity.KEY_SELECTED);

            linearLayoutExcept.removeAllViews();
            for (int index = 0; index < exceptList.size(); index++) {
                linearLayoutExcept.addView(BallHelper.makeNumberBall(this, exceptList.get(index)));
            }
        }
    }

}
