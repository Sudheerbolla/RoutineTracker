package com.routinetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.routinetracker.R;
import com.routinetracker.utils.AppStorage;
import com.routinetracker.utils.Constants;

public class SplashActivity extends BaseActivity {

    @Override
    void initComponents() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!TextUtils.isEmpty(AppStorage.getInstance(this).getValue(AppStorage.SP_USER_ID, ""))) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, Constants.SPLASH_LOADING_TIME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initComponents();
    }
}