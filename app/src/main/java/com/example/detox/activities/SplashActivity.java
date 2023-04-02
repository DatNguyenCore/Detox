package com.example.detox.activities;

import android.os.Bundle;
import android.os.Handler;

import com.example.detox.MainActivity;
import com.example.detox.R;
import com.example.detox.configs.AppSharedPreferences;

public class SplashActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final AppSharedPreferences appSharedPreferences = new AppSharedPreferences(this);

        new Handler().postDelayed((Runnable) () -> {
            if(appSharedPreferences.getBooleanValue(AppSharedPreferences.INSTALLED_APP)) {
                startActivity(MainActivity.class);
            } else {
                startActivity(OnBoardingActivity.class);
            }
        }, 300);
    }
}