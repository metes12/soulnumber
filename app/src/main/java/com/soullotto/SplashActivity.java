package com.soullotto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.soullotto.IntroActivity;
import com.soullotto.SoulNumberActivity;
import com.soullotto.utils.SoulNumberHelper;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int birthDay = SoulNumberHelper.getBirthDay(getApplicationContext());

        //생일을 한번도 입력받은 적이 없으면 1로 리턴된다.
        Intent intent;
        if (birthDay == 1) {
            intent = new Intent(this, IntroActivity.class);
        } else {
            intent = new Intent(this, SoulNumberActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
