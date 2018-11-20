package com.soullotto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.ColorBall;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.DialogHelper;
import com.soullotto.utils.SoulNumberHelper;

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
                .backgroundColor(R.color.red)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.lotto_machine)
                .title("Soul Lotto")
                .description("Soul Lotto에 오신 것을\n환영합니다!")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.green)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.lotto_ball_intro)
                .description("Soul Lotto는\n생년월일 및\n오늘의 숫자를 바탕으로\n로또 번호를 이쁘게 뽑아줍니다")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.bpBlue)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.lotto_machine)
                .description("운명의 숫자\n오늘의 숫자가 궁금하신가요?\n소울로또를 시작해봅시다!")
                .build(), new MessageButtonBehaviour(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .setDoneText("확인");

                cdp.show(getSupportFragmentManager(), "tag");

            }
        }, "생년월일 입력하기"));

        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

    }


    @Override
    public void onFinish() {
        Toast.makeText(this, "생년월일을 입력해주세요!", Toast.LENGTH_SHORT).show();
    }
}