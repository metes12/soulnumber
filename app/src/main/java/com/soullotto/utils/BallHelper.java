package com.soullotto.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.soullotto.commons.Constants;
import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.ColorBall;

import java.util.List;

public class BallHelper {

    public static Button makeNumberBall(Context context, int number) {
        ToggleButton toggleButton = makeNumberBallToggle(context, number);
        toggleButton.setChecked(true);
        toggleButton.setOnCheckedChangeListener(null);

        return toggleButton;
    }

    public static ToggleButton makeNumberBallToggle(final Context context, int number) {
        int btnSize = (int)dpToPixel(context, Constants.DP_BUTTON_SIZE);
        final ToggleButton button = new ToggleButton(context);
        button.setText(String.valueOf(number));
        button.setBackground(context.getResources().getDrawable(R.drawable.ic_number_on));
        button.setLayoutParams(new ViewGroup.LayoutParams(btnSize, btnSize));
        button.setText(String.valueOf(number));
        button.setPadding(2, 2, 2, 2);
        makeColorBall(button);

        button.setTextOn(String.valueOf(number));
        button.setTextOff(String.valueOf(number));
        button.setTextColor(Color.WHITE);
        button.setBackground(context.getDrawable(R.drawable.toggle_selector));

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    int color = Color.parseColor(ColorBall.getColorByNumber(Integer.parseInt(buttonView.getText().toString())).getColor());
                    Drawable drawable = context.getDrawable(R.drawable.ic_number_on);

                    if (drawable != null) {
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                        button.setBackground(drawable);
                    }
                } else {
                    int color = Color.parseColor("#CCC0BD");
                    Drawable drawable = context.getDrawable(R.drawable.ic_number_on);

                    if (drawable != null) {
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                        button.setBackground(drawable);
                    }
                }
            }
        });

        button.setChecked(true);
        button.setChecked(false);

        return button;
    }

    @SuppressLint("ResourceType")
    public static void makeColorBall(Button view) {
        int number = Integer.parseInt(view.getText().toString());
        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ColorBall.getColorByNumber(number).getColor())));
    }

    public static float dpToPixel(Context context, float dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}
