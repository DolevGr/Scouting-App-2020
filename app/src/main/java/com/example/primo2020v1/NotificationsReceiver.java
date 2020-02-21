package com.example.primo2020v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DrawerActivity drawerActivity = new DrawerActivity();
        drawerActivity.stopService();
    }
}
