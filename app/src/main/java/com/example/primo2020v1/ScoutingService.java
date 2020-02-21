package com.example.primo2020v1;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.primo2020v1.libs.Keys;

public class ScoutingService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra(Keys.INPUT_PIT);

        Intent notificationsIntent = new Intent(this, DrawerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationsIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationsReceiver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, Scouting.CHANNEL_PIT)
                .setContentText("Pit Service")
                .setContentText(input)
                .setColor(getResources().getColor(R.color.mainBlue))
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_app, "Close", actionIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
