package com.example.detox.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.detox.R;
import com.example.detox.configs.DurationLock;
import com.example.detox.fragments.HomeFragment;
import com.example.detox.windows.LockWindow;

public class LockService extends Service {

    private static final String TAG = "ForegroundService";
    private final int NOTIFICATION_ID = 1;

    public LockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // create the custom or default notification
        // based on the android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundNotification();
        else
            startForeground(NOTIFICATION_ID, new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long duration = intent.getLongExtra(HomeFragment.HONE_INTENT_DURATION_OVERLAY, DurationLock.NONE);

        if(duration != DurationLock.NONE) {
            LockWindow window = new LockWindow(this);
            window.open(duration);
        } else {
            this.stopSelf();
        }

        return START_STICKY;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startForegroundNotification() {
        String NOTIFICATION_CHANNEL_ID = "detox.permanence";
        String channelName = "Detox";
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Detox")
                .setContentText("Nowhere to go back, focus on your work")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }
}
