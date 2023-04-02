package com.example.detox.windows;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.detox.R;

public class ConfirmWindow extends Dialog implements View.OnClickListener {

    private Button allowBtn, noAllowBtn;

    private static ConfirmWindow instance;

    private final OnConfirmWindowClickListener listener;


    private ConfirmWindow(Activity a, OnConfirmWindowClickListener listener) {
        super(a);

        this.listener = listener;
    }

    public static ConfirmWindow getInstance(Activity activity, OnConfirmWindowClickListener listener) {
        if(instance == null)  {
            instance = new ConfirmWindow(activity, listener);
        }

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirm_window);
        allowBtn = findViewById(R.id.btn_confirm_window_allow);
        noAllowBtn =  findViewById(R.id.btn_confirm_window_no_allow);

        allowBtn.setOnClickListener(this);
        noAllowBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_window_allow:
                listener.onConfirm(true);
                break;
            case R.id.btn_confirm_window_no_allow:
                listener.onConfirm(false);
                break;
            default:
                break;
        }

        dismiss();
    }
}
