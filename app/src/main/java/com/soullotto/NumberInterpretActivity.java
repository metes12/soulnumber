package com.soullotto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.SoulNumberHelper;

import java.util.ArrayList;
import java.util.List;

public class NumberInterpretActivity extends Activity {

    private WebView webView;

    private static final String URL = "file:///android_asset/local.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_interpret);
        WebView webView = findViewById(R.id.webv_interpret);

        webView.loadUrl(URL);


        String[] interpretArray = getResources().getStringArray(R.array.numberInterpret);

        LottoCreator lottoCreator = new LottoCreator(SoulNumberHelper.getBirthDay(getApplicationContext()));
        int soulNumber = lottoCreator.getSoulNumber();

        soulNumber = ((soulNumber / 10) + (soulNumber % 10));

    }
}
