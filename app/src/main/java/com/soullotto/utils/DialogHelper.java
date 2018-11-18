package com.soullotto.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import com.soullotto.SoulNumberActivity;
import com.soullotto.commons.Constants;
import com.soullotto.soullotto.R;
import com.soullotto.utils.dialog.Animation;
import com.soullotto.utils.dialog.FancyAlertDialog;
import com.soullotto.utils.dialog.FancyAlertDialogListener;
import com.soullotto.utils.dialog.Icon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DialogHelper {

    public static void showSoulNumberDialog(final Activity activity, final int soulNumber, final int birthDay) {
        new FancyAlertDialog.Builder(activity)
                .setTitle("당신의 소울 넘버는...")
                .setBackgroundColor(Color.parseColor("#303F9F"))
                .setMessage2("입니다!")
                .setMessage(String.valueOf(soulNumber))
                .setPositiveBtnText("시작하기")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp,Icon.Visible)
                .setNegativeBtnVisibility(View.GONE)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SoulNumberHelper.saveBirthDay(birthDay, activity.getApplicationContext());

                        Intent intent = new Intent(activity, SoulNumberActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                    }
                })
                .build();
    }

}
