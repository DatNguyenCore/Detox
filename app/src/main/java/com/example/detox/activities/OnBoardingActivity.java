package com.example.detox.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.detox.MainActivity;
import com.example.detox.configs.AppSharedPreferences;
import com.example.detox.databinding.ActivityOnBoardingBinding;
import com.example.detox.windows.ConfirmWindow;
import com.example.detox.windows.OnConfirmWindowClickListener;

public class OnBoardingActivity extends BaseCompatActivity implements OnConfirmWindowClickListener {
    private static final String TAG = "OnBoardingActivity";
    public static final String HONE_INTENT_DURATION_OVERLAY = "HONE_INTENT_DURATION_OVERLAY";

    private ActivityOnBoardingBinding binding;
    private AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appSharedPreferences = new AppSharedPreferences(this);

        binding.btnStartOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmWindow.getInstance(
                        OnBoardingActivity.this,
                        OnBoardingActivity.this).show();
            }
        });
    }


    private void requestOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.getPackageName()));
//        mActivityResultLauncher.launch(intent);
        startActivity(intent);
    }

    @Override
    public void onConfirm(boolean allow) {
        if(allow) {
            requestOverlayPermission();
        } else  {
            startActivity(MainActivity.class);
        }

        appSharedPreferences.putBooleanValue(AppSharedPreferences.INSTALLED_APP, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        startActivity(MainActivity.class);
    }
}