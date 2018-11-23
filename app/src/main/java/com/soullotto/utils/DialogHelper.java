package com.soullotto.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.soullotto.SoulNumberActivity;
import com.soullotto.SoulNumberSolveActivity;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.dialog.Animation;
import com.soullotto.utils.dialog.FancyAlertDialog;
import com.soullotto.utils.dialog.FancyAlertDialogListener;
import com.soullotto.utils.dialog.Icon;

import java.util.Calendar;

public class DialogHelper {

    public static void showBirthDayDialog(final AppCompatActivity activity, boolean cancelable) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        String birthDayYmdt = String.valueOf(year) + String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth);
                        LottoCreator lottoCreator = new LottoCreator(Integer.parseInt(birthDayYmdt));
                        DialogHelper.showSoulNumberDialog(activity, lottoCreator.getSoulNumber(), Integer.parseInt(birthDayYmdt));
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCancelText(null)
                .setDialogCancelable(cancelable)
                .setDoneText(activity.getString(R.string.confirm));

        cdp.show(activity.getSupportFragmentManager(), "tag");
    }

    public static void showSoulNumberDialog(final Activity activity, final int soulNumber, final int birthDay) {
        new FancyAlertDialog.Builder(activity)
                .setTitle("당신의 소울 넘버는...")
                .setBackgroundColor(Color.parseColor("#303F9F"))
                .setMessage2("입니다!")
                .setMessage(String.valueOf(soulNumber))
                .setPositiveBtnText("시작하기")
                .setNegativeBtnText("해석보기")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp,Icon.Visible)
                .setNegativeBtnVisibility(View.VISIBLE)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SoulNumberHelper.saveBirthDay(birthDay, activity.getApplicationContext());

                        Intent intent = new Intent(activity, SoulNumberActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SoulNumberHelper.saveBirthDay(birthDay, activity.getApplicationContext());
                        Intent intent = new Intent(activity, SoulNumberSolveActivity.class);
                        intent.putExtra(SoulNumberSolveActivity.PARAM_INITIAL_PAGE, true);
                        activity.startActivity(intent);
                    }
                })
                .build();
    }

}
