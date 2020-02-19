package com.example.primo2020v1;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DrawerActivity extends AppCompatActivity {
    private static final String CHANNEL_ID_1 = "channel1";
    private static final String TAG = "DrawerActivity";

    private DatabaseReference dbRef = User.databaseReference;
    private NotificationManagerCompat managerCompat;
    private Intent intent;
    private String username, rank;
    private AppBarConfiguration mAppBarConfiguration;
    private int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        intent = getIntent();
        if (intent.hasExtra("Username")) {
            username = intent.getStringExtra("Username");
            User.username = username;
            rank = intent.getStringExtra("Rank");
            User.userRank = rank;
//            TextView tvHeader = findViewById(R.id.tvHeader);
//            String info = "Hello " + username;
//            tvHeader.setText(info);
        }

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.currentGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        res = R.id.navMatches;
        if (intent.hasExtra("Navigation")) {
            res = intent.getIntExtra("Navigation", R.id.navMatches);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (User.masterRanks.contains(User.userRank)) {
            setSupportActionBar(toolbar);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navMatches, R.id.navTeamOverview, R.id.navAbilityRating,
                    R.id.navTeamComparision, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(res);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        if (User.userRank.equals("Pit")) {
            managerCompat = NotificationManagerCompat.from(this);
            Log.d(TAG, "onCreate: " + User.currentGame);
            if (User.matches.get(User.currentGame + 3).hasTeam("4585")) {
                Log.d(TAG, "123 ");
                Notification notification = new NotificationCompat.Builder(this, Notifications.CHANNEL_ID_1)
//                        .setSmallIcon()
                        .setContentTitle("Title")
                        .setContentText("Channel 1")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                managerCompat.notify(1, notification);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
