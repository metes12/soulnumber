package com.soullotto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.soullotto.soullotto.R;

public class SoulNumberActivity extends Activity {

    private ImageView imgvCrystal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calender);
        imgvCrystal = findViewById(R.id.imgv_crystal);

        Glide.with(this)
            .load(R.drawable.crystal)
            .into(imgvCrystal);
    }
}
