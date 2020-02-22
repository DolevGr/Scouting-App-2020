package com.example.primo2020v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
    private static String username, rank;
    private AppBarConfiguration mAppBarConfiguration;
    private int res;

    private BroadcastReceiver reciver;

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

        //Navigation view config
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

        //Notifications config
        if (User.userRank.equals("Pit") || User.masterRanks.contains(User.userRank)) {
            //startService();
            stopService(new Intent(this, ScoutingService.class));
            startService(new Intent(this, ScoutingService.class));
        }

        reciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("tag", "onReceive: received");
                String s = intent.getStringExtra(ScoutingService.COPA_MESSAGE);
            }
        };
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

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((reciver),
                new IntentFilter(ScoutingService.COPA_RESULT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).registerReceiver((reciver),
                new IntentFilter(ScoutingService.COPA_RESULT)
        );
    }
}
