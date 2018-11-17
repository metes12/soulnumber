package com.soullotto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.dialog.Animation;
import com.soullotto.utils.dialog.FancyAlertDialog;
import com.soullotto.utils.dialog.FancyAlertDialogListener;
import com.soullotto.utils.dialog.Icon;

import java.util.Calendar;

public class LottoMainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private static final String ADMOB_ID = "ca-app-pub-1611757228618027~9373727028";
    private static final String ADMOB_REWARD_ID = "ca-app-pub-1611757228618027/6901855127";

    private RewardedVideoAd mRewardedVideoAd;

    private LottoCreator lottoCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MobileAds.initialize(this, ADMOB_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);


        loadRewardedVideoAd();

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
