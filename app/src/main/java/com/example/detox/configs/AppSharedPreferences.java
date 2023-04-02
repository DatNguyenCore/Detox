package com.example.detox.configs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {
    private static final String APP_SHARE_PREFERENCE = "APP_SHARE_PREFERENCE";

    public static final String INSTALLED_APP = "INSTALLED_APP";
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public AppSharedPreferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(APP_SHARE_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }
}
