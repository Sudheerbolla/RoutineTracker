package com.routinetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.routinetracker.R;
import com.routinetracker.utils.AppStorage;
import com.routinetracker.utils.Constants;

public class SplashActivity extends BaseActivity {

    @Override
    void initComponents() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (AppStorage.getInstance(this).getValue(AppStorage.SP_USER_ID, -1L) == -1) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            finish();
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