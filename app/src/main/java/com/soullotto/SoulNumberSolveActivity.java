package com.soullotto;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.soullotto.soullotto.R;
import com.soullotto.soulnumber.LottoCreator;
import com.soullotto.utils.SoulNumberHelper;

public class SoulNumberSolveActivity extends AppCompatActivity {

    public static final String PARAM_INITIAL_PAGE = "initial_page";

    private boolean initialPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialPage = getIntent().getBooleanExtra(PARAM_INITIAL_PAGE, false);

        setContentView(R.layout.activity_empty);
        RelativeLayout root = findViewById(R.id.root);
        WebView webView = new WebView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);

        Resources res = getResources();
        String[] solveArray = res.getStringArray(R.array.solve_array);

        int birthDay = SoulNumberHelper.getBirthDay(getApplicationContext());
        LottoCreator lottoCreator = new LottoCreator(birthDay);
        int soulNumber = lottoCreator.getSoulNumber();
        int fileIndex = (soulNumber / 10) + (soulNumber % 10);

        String filePath = solveArray[fileIndex];

        root.addView(webView);
        webView.loadUrl(filePath);
    }

    @Override
    public void onBackPressed() {
        if (initialPage) {
            Intent intent = new Intent(this, SoulNumberActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        super.onBackPressed();
    }
}
