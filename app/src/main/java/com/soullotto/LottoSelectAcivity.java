package com.soullotto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.soullotto.commons.Constants;
import com.soullotto.soullotto.R;
import com.soullotto.utils.BallHelper;

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

        setContentView(R.layout.activity_lotto_select);

        String type = getIntent().getStringExtra(KEY_TYPE);
        TextView textView = findViewById(R.id.txtv_select);
        btnSelectDone = findViewById(R.id.btn_select_done);

        if (type.equals(TYPE_EXCEPT)) {
            textView.setText("제외할 숫자를 선택하세요.");
        } else {
            textView.setText("포함할 숫자를 선택하세요.");
        }

        gridLayout = findViewById(R.id.table_layout);
        gridLayout.setColumnCount(5);

        for (int i=0; i<45; i++) {
            ToggleButton toggleButton = BallHelper.makeNumberBallToggle(this, i + 1);
            gridLayout.addView(toggleButton);
        }
    }

    public void onDoneButtonClicked(View v) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ToggleButton button = (ToggleButton)gridLayout.getChildAt(i);

            if (button.isChecked()) {
                String selectedString = button.getText().toString();
                arrayList.add(Integer.parseInt(selectedString));
            }
        }

        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(KEY_SELECTED, arrayList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
