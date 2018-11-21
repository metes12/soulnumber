package com.soullotto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.DialogHelper;

import java.util.Calendar;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;


public class IntroActivity extends MaterialIntroActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.yellow)
                .buttonsColor(R.color.colorAccent)
                .image(R.raw.favourite_app_icon)
                .title(getString(R.string.soul_lotto_title))
                .description(getString(R.string.soul_lotto_description_1))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.green)
                .buttonsColor(R.color.colorAccent)
                .image(R.raw.gears)
                .description(getString(R.string.soul_lotto_description_2))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.moon)
                .buttonsColor(R.color.colorAccent)
                .image(R.raw.moon)
                .description(getString(R.string.soul_lotto_description_3))
                .build(), new MessageButtonBehaviour(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        }, getString(R.string.enter_birthday)));

        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

    }

    private void showDialog() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        String birthDayYmdt = String.valueOf(year) + String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth);
                        LottoCreator lottoCreator = new LottoCreator(Integer.parseInt(birthDayYmdt));
                        DialogHelper.showSoulNumberDialog(IntroActivity.this, lottoCreator.getSoulNumber(), Integer.parseInt(birthDayYmdt));
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCancelText(null)
                .setDoneText(getString(R.string.confirm));

        cdp.show(getSupportFragmentManager(), "tag");
    }


    @Override
    public void onFinish() {
        showDialog();
    }
}