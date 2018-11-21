package com.soullotto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.MobileAds;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.soulnumber.LottoNumbers;
import com.soullotto.utils.BallHelper;
import com.soullotto.utils.DialogHelper;
import com.soullotto.utils.SoulNumberHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SoulNumberActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private static final String ADMOB_ID = "ca-app-pub-1611757228618027~9373727028";
    private static final String ADMOB_REWARD_ID = "ca-app-pub-1611757228618027/6901855127";

    public static final int MS_SHAKE_TIME = 3500;

    public static final int REQ_CODE_SELECT_NUMBER_INCLUDE = 1;
    public static final int REQ_CODE_SELECT_NUMBER_EXCEPT = 2;
    public static final int ALARM_REQUEST_CODE = 0;

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

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_soul_number);

        initializeAdmob();

        lottoCreator = new LottoCreator(SoulNumberHelper.getBirthDay(getApplicationContext()));

        rfaLayout = findViewById(R.id.activity_main_rfal);
        rfaBtn = findViewById(R.id.activity_main_rfab);

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

        initializeMenu();
    }

    private void initializeAdmob() {
        MobileAds.initialize(this, ADMOB_ID);
    }

    public void onIncludeButtonClicked() {
        Intent intent = new Intent(this, LottoSelectAcivity.class);
        intent.putExtra(LottoSelectAcivity.KEY_TYPE, LottoSelectAcivity.TYPE_INCLUDE);
        startActivityForResult(intent, REQ_CODE_SELECT_NUMBER_INCLUDE);
    }

    public void onExceptButtonClicked() {
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
                final int [] exceptArray = BallHelper.convertIntegers(exceptList);
                final int [] includeArray = BallHelper.convertIntegers(includeList);

                final LottoNumbers lottoNumbers = new LottoNumbers();

                for (int i=0; i<5; i++) {
                    lottoNumbers.add(lottoCreator.getIncludeExcept(exceptArray, includeArray));
                    SystemClock.sleep(MS_SHAKE_TIME / 5);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnLotto.setVisibility(View.VISIBLE);
                        imgvShake.setVisibility(View.GONE);
                        linearLayoutNumbers.setVisibility(View.VISIBLE);

                        gridLotto.removeAllViews();

                        for (int i = 0; i < 5; i++) {
                            int[] lotto = lottoNumbers.getLottoMap(i);

                            for (int j = 0; j < lotto.length; j++) {
                                gridLotto.addView(BallHelper.makeNumberBall(SoulNumberActivity.this, lotto[j]));
                            }
                        }

                        SoulNumberHelper.saveLottoNumber(SoulNumberActivity.this, lottoNumbers);
                    }
                });
            }
        }).start();
    }

    private void onLicenseButtonClicked() {

    }

    private void onInitBirthdayButtonClicked() {
        DialogHelper.showBirthDayDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == REQ_CODE_SELECT_NUMBER_INCLUDE) {
                includeList = data.getIntegerArrayListExtra(LottoSelectAcivity.KEY_SELECTED);

                refreshIncludeExceptView(linearLayoutInclude, includeList);
                SoulNumberHelper.saveIncludeNumber(this, includeList);
            } else if (requestCode == REQ_CODE_SELECT_NUMBER_EXCEPT) {
                exceptList = data.getIntegerArrayListExtra(LottoSelectAcivity.KEY_SELECTED);

                refreshIncludeExceptView(linearLayoutExcept, exceptList);
                SoulNumberHelper.saveExceptNumber(this, exceptList);
            }
        }
    }

    private void refreshIncludeExceptView(LinearLayout linearLayoutInclude, List<Integer> includeList) {
        if (includeList == null) {
            return;
        }

        linearLayoutInclude.removeAllViews();
        for (int index = 0; index < includeList.size(); index++) {
            linearLayoutInclude.addView(BallHelper.makeNumberBall(this, includeList.get(index)));
        }
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        onMenuClicked(item.getLabel());
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        onMenuClicked(item.getLabel());
    }

    private void onMenuClicked(String label) {
        rfabHelper.toggleContent();

        if (label.equals(getString(R.string.except_number))) {
            onExceptButtonClicked();
        } else if (label.equals(getString(R.string.include_number))) {
            onIncludeButtonClicked();
        } else if (label.equals(getString(R.string.init_birthday))) {
            onInitBirthdayButtonClicked();
        } if (label.equals(getString(R.string.creator_license))) {
            onLicenseButtonClicked();
        }
    }

    private void initializeMenu() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.creator_license))
                .setResId(R.drawable.settings)
                .setIconNormalColor(0xff283593)
                .setIconPressedColor(0xff1a237e)
                .setLabelColor(0xff283593)
                .setWrapper(3)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.init_birthday))
                .setResId(R.drawable.settings)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(2)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.include_number))
                .setResId(R.drawable.settings)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(RFABShape.generateCornerShapeDrawable(0xaa000000, RFABTextUtil.dip2px(this, 4)))
                .setWrapper(1)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.except_number))
                .setResId(R.drawable.settings)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        rfaContent
                .setItems(items);
        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

        //initialize alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, LottoNumberReceiver.class);
        PendingIntent pi = PendingIntent.getService(this, ALARM_REQUEST_CODE, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pi);

        //initialize except, include views
        includeList = SoulNumberHelper.getIncludeNumber(this);
        exceptList = SoulNumberHelper.getExceptNumber(this);

        refreshIncludeExceptView(linearLayoutInclude, includeList);
        refreshIncludeExceptView(linearLayoutExcept, exceptList);

        //initialize lotto view
        LottoNumbers lottoNumbers = SoulNumberHelper.getLottoNumber(this);

        if (lottoNumbers != null) {
            gridLotto.removeAllViews();
            for (int i = 0; i< lottoNumbers.getSize(); i++) {
                int[] lotto = lottoNumbers.getLottoMap(i);
                for (int j = 0; j < lotto.length; j++) {
                    gridLotto.addView(BallHelper.makeNumberBall(SoulNumberActivity.this, lotto[j]));
                }
            }
        }
    }
}
