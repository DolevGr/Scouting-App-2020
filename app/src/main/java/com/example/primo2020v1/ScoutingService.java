package com.example.primo2020v1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ScoutingService extends Service {

    private final IBinder mBinder = new ServiceBinder();
    public static boolean isServiceRunning = false;
    static final public String COPA_RESULT = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";
    static final public String COPA_MESSAGE = "com.controlj.copame.backend.COPAService.COPA_MSG";
    boolean ran = false; //spam controller


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public class ServiceBinder extends Binder {
        ScoutingService getService() {
            return ScoutingService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = "Match starting in 3 games";

        Log.d("Tag Service is in???", "" + User.matches.get(User.liveMatch + 2).hasTeam("4586"));
        Log.d("Tag Service is in???", "onStartCommand: " + User.liveMatch + "\n" +
                User.matches.get(User.liveMatch + 2));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);
            String description = getString(R.string.common_google_play_services_notification_channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("M_CH_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationsIntent = new Intent(this, SplashActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0, notificationsIntent, 0);

        Notification notiBuilder;

        notiBuilder = new NotificationCompat.Builder(this, Scouting.CHANNEL_PIT)
                .setContentTitle("Scouter")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(input))
                .setColor(getResources().getColor(R.color.mainBlue))
                .setSmallIcon(R.mipmap.ic_app)
//                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true).build();

//        startForeground(12345, notiBuilder);

        User.databaseReference.child(Keys.CURRENT_GAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "before: " + User.liveMatch);
                User.liveMatch = Integer.parseInt(dataSnapshot.getValue().toString());
                Log.d(TAG, "after: " + User.liveMatch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        User.databaseReference.child(Keys.CURRENT_GAME).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                User.liveMatch = Integer.parseInt(s);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //TODO: add pattern of allstar to vibrator effect
        if (User.matches.get(User.liveMatch + 2) != null
                && User.matches.get(User.liveMatch + 2).hasTeam("4586")) {
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            long[] allpattern = {0,134,169,230,412,62,89,52,80,80,237,278,314,56,88,54,61,72,247,263,372,296,325,477,424,107,189,315,285,45,78,56,68,83,253,288,297,54,97,45,70,97,226,326,324,328,244,447,463,346,226,365,267,134,162,197,127,150,151,408,141,524,329,71,195,89,186,142,135,95,187,122,182,470,320,129,167,310,209,396,156,163,162,767,0};
            //{0,64,194,88,420,32,133,13,76,60,235,65,437,31,97,39,78,61,230,46,504,68,492,86,702,51,219,55,471,31,108,33,50,58,271,45,461,39,98,37,60,67,246,48,505,66,438,79,690,137,388,214,322,78,198,54,206,59,225,58,187,83,932,59,201,56,211,71,195,54,215,80,193,66,493,50,204,61,204,48,437,76,372,65,297,279,0};
            v.vibrate(allpattern, -1);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(12345, notiBuilder);
        return START_STICKY;

    }


    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void SendResult(String msg) {
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(COPA_RESULT);
        if (msg != null)
            intent.putExtra(COPA_MESSAGE, msg);
        broadcaster.sendBroadcast(intent);
    }
}
