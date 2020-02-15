package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.currentGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
                setUsersNames(dataSnapshot);
                setMatches(dataSnapshot);
                setAllTeams(dataSnapshot);

                in = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        User.admins.add("Dolev");
        User.admins.add("Iair");
        User.admins.add("Shoshana");
        User.admins.add("Tohar");
        User.admins.add("Samuel");
        User.admins.add("Mor");
        User.admins.add("Tal");
    }

    private void setMatches(DataSnapshot dataSnapshot) {
        DataSnapshot dsMatches = dataSnapshot.child(Keys.MATCHES);
        for (DataSnapshot ds : dsMatches.getChildren()) {
            Match m = ds.getValue(Match.class);
            m.setGameNum(Integer.parseInt(ds.getKey()));
            User.matches.add(m);
        }
        Log.d(TAG, "setMatches: " + User.matches.toString());
    }

    private void setUsersNames(DataSnapshot dataSnapshot) {
        DataSnapshot dsUsers = dataSnapshot.child(Keys.USERS);
        for (DataSnapshot ds : dsUsers.getChildren()) {
            User u = ds.getValue(User.class);
            User.members.add(u.getName());
        }
        Log.d(TAG, "setUsersNames: " + User.members.toString());
    }

    private void setAllTeams(DataSnapshot dataSnapshot) {
        DataSnapshot dsTeams = dataSnapshot.child("Participants");

        for (DataSnapshot ds : dsTeams.getChildren()) {
            User.teams.put(Integer.parseInt(ds.getKey()), ds.getValue().toString());
        }
    }
}
