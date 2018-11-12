package com.soullotto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.soullotto.soullotto.R;
import com.soullotto.utils.dialog.Animation;
import com.soullotto.utils.dialog.FancyAlertDialog;
import com.soullotto.utils.dialog.FancyAlertDialogListener;
import com.soullotto.utils.dialog.Icon;

public class LottoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showSoulNumberDialog();
    }

    private void showSoulNumberDialog() {
        new FancyAlertDialog.Builder(this)
                .setTitle("당신의 소울 넘버는...")
                .setBackgroundColor(Color.parseColor("#303F9F"))
                .setMessage2("입니다!")
                .setMessage("8")
                .setNegativeBtnText("해석보기")
                .setPositiveBtnText("시작하기")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp,Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(),"시작--",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(),"해석--",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
}
