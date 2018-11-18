package com.soullotto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.soullotto.soullotto.R;

import java.util.ArrayList;

public class LottoSelectAcivity extends Activity {

    public static String KEY_TYPE = "KEY_TYPE";
    public static String TYPE_EXCEPT = "KEY_EXCEPT";
    public static String TYPE_INCLUDE = "KEY_INCLUDE";
    public static String KEY_SELECTED = "KEY_SELECTED";
    private GridLayout gridLayout;

    private Button btnSelectDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String type = getIntent().getStringExtra(KEY_TYPE);
        TextView textView = findViewById(R.id.txtv_select);
        btnSelectDone = findViewById(R.id.btn_select_done);

        if (type.equals(TYPE_EXCEPT)) {
            textView.setText("제외할 숫자를 선택하세요.");
        } else {
            textView.setText("포함할 숫자를 선택하세요.");
        }

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

    public void onDoneButtonClicked(View v) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            String selectedString = ((ToggleButton)gridLayout.getChildAt(i)).getText().toString();
            arrayList.add(Integer.parseInt(selectedString));
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED, arrayList);
        setResult(RESULT_OK, intent);
        finish();
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
