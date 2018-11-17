package com.soullotto;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.ToggleButton;

import com.soullotto.soullotto.R;

public class LottoSelectAcivity extends Activity {

    GridLayout gridLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_select);
        gridLayout = findViewById(R.id.table_layout);
        gridLayout.setColumnCount(5);

        int btnSize = (int)dpToPixel(50f);
        for (int i=0; i<45; i++) {
            ToggleButton button = new ToggleButton(LottoSelectAcivity.this);
            button.setTextOn(String.valueOf(i + 1));
            button.setTextOff(String.valueOf(i + 1));
            button.setBackground(getResources().getDrawable(R.drawable.toggle_selector));
            button.setLayoutParams(new ViewGroup.LayoutParams(btnSize, btnSize));
            button.setText(String.valueOf(i + 1));
            gridLayout.addView(button);
        }
    }

    private float dpToPixel(float dp) {
        Resources r = getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
}
