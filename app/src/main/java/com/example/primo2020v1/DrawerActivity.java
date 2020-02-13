package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.primo2020v1.libs.User;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity {
    private Intent intent;
    private String username;
    private static boolean priv;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        intent = getIntent();
        if (intent.hasExtra("Username")) {
            username = intent.getStringExtra("Username");
            User.username = username;
            priv = intent.getBooleanExtra("Privilege", false);
//            TextView tvHeader = findViewById(R.id.tvHeader);
//            String info = "Hello " + username;
//            tvHeader.setText(info);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (priv) {
            setSupportActionBar(toolbar);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navMatches, R.id.navTeamOverview, R.id.navAbilityRating,
                    R.id.navTeamComparision, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
