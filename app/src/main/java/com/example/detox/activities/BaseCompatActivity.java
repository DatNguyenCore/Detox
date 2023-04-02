package com.example.detox.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseCompatActivity extends AppCompatActivity {
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

}
