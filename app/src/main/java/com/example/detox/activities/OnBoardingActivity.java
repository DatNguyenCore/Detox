package com.example.detox.activities;

import android.os.Bundle;
import android.view.View;

import com.example.detox.MainActivity;
import com.example.detox.databinding.ActivityOnBoardingBinding;
import com.example.detox.windows.ConfirmWindow;
import com.example.detox.windows.OnConfirmWindowClickListener;

public class OnBoardingActivity extends BaseCompatActivity implements OnConfirmWindowClickListener {

    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmWindow.getInstance(
                        OnBoardingActivity.this,
                        OnBoardingActivity.this).show();
            }
        });
    }

    @Override
    public void onConfirm(boolean allow) {
        if(allow) {
            startActivity(MainActivity.class);
        } else  {
            startActivity(MainActivity.class);
        }
    }
}