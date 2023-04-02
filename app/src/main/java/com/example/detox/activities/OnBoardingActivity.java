package com.example.detox.activities;

import android.os.Bundle;
import android.view.View;

import com.example.detox.MainActivity;
import com.example.detox.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends BaseCompatActivity {

    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
            }
        });
    }

}