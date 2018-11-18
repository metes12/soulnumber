package com.soullotto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.soullotto.commons.Constants;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.SoulNumberHelper;

public class SoulNumberActivity extends Activity implements RewardedVideoAdListener {

    private static final String ADMOB_ID = "ca-app-pub-1611757228618027~9373727028";
    private static final String ADMOB_REWARD_ID = "ca-app-pub-1611757228618027/6901855127";
    public static final int MS_SHAKE_TIME = 4000;

    private LottoCreator lottoCreator;
    private Button imgvSoul;
    private Button imgvToday;
    private Button btnLotto;
    private LinearLayout imgvShake;
    private LinearLayout linearLayoutNumbers;

    private RewardedVideoAd mRewardedVideoAd;

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

        linearLayoutNumbers = findViewById(R.id.linerv_numbers);
        imgvSoul.setText(String.valueOf(lottoCreator.getSoulNumber()));
        imgvToday.setText(String.valueOf(lottoCreator.getTodayNumber(this)));

    }

    private void initializeAdmob() {
        MobileAds.initialize(this, ADMOB_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    public void onIncludeButtonClicked(View view) {
        Intent intent = new Intent(this, LottoSelectAcivity.class);
        intent.putExtra(LottoSelectAcivity.KEY_TYPE, LottoSelectAcivity.TYPE_INCLUDE);
        startActivity(intent);
    }

    public void onExceptButtonClicked(View view) {
        Intent intent = new Intent(this, LottoSelectAcivity.class);
        intent.putExtra(LottoSelectAcivity.KEY_TYPE, LottoSelectAcivity.TYPE_EXCEPT);
        startActivity(intent);
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
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    /**
     * admob 비디오 광고 로딩
     */
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(ADMOB_REWARD_ID, new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {}

    @Override
    public void onRewardedVideoStarted() {}

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {}

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        mRewardedVideoAd.loadAd(ADMOB_REWARD_ID, new AdRequest.Builder().build());

    }

    @Override
    public void onRewardedVideoCompleted() {}

}
