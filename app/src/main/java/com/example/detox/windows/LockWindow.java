package com.example.detox.windows;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

import com.example.detox.R;
import com.example.detox.services.LockService;

public class LockWindow {

    private static final long COUNT_DOWN_10_MINUS = 600000;
    private static final long COUNT_DOWN_10_SECONDS = 10000;

    // declaring required variables
    private Context context;
    private View layoutView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;
    private TextView mMinute, mSecond;
    private CountDownTimer mCountDownTimer;
    private Button mBtnStop;

    public LockWindow(Context context) {
        this.context = context;

        this.initView();
        countDownStart();

    }

    private void countDownStart() {
        mCountDownTimer = new CountDownTimer(this.COUNT_DOWN_10_MINUS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long totalSeconds = millisUntilFinished / 1000;
                long second = totalSeconds % 60;
                long minute = (long) Math.floor(totalSeconds / 60);

                mMinute.setText(String.format("%02d", minute));
                mSecond.setText(String.format("%02d", second));

            }

            @Override
            public void onFinish() {
                LockWindow.this.close();
            }
        }.start();

        new CountDownTimer(COUNT_DOWN_10_SECONDS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long totalSeconds = millisUntilFinished / 1000;

                mBtnStop.setText(String.valueOf("Stop your freedom " + "(" + totalSeconds) + ")");
                if (totalSeconds < 1) {
                    mBtnStop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    // Don't let it grab the input focus
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // Make the underlying application window visible
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);

        }
        // getting a LayoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        layoutView = layoutInflater.inflate(R.layout.lock_window, null);

        mBtnStop = layoutView.findViewById(R.id.button_lock_stop_detox);

        mMinute = layoutView.findViewById(R.id.text_lock_min);
        mSecond = layoutView.findViewById(R.id.text_lock_second);
        // set onClickListener on the remove button, which removes
        // the view from the window
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        // Define the position of the
        // window within the screen
        mParams.gravity = Gravity.FILL;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }

    public void open() {
        try {
            // check if the view is already
            // inflated or present in the window
            if (layoutView.getWindowToken() == null) {
                if (layoutView.getParent() == null) {
                    mWindowManager.addView(layoutView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1", e.toString());
        }
    }

    public void close() {
        try {
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }

            Intent intent = new Intent(context, LockService.class);
            context.stopService(intent);

            // remove the view from the window
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(layoutView);
            // invalidate the view
            layoutView.invalidate();
            // remove all views
            if (layoutView.getParent() != null) {
                ((ViewGroup) layoutView.getParent()).removeAllViews();
            }

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions

        } catch (Exception e) {
            Log.d("close error", e.toString());
        }
    }
}
