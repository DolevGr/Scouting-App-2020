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
    boolean isFirstEntry = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setAllTeams();
        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.currentGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());

                if (isFirstEntry) {
                    isFirstEntry = false;
                    setUsersNames(dataSnapshot);
                    setMatches(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        User.admins.add("Dolev");
        User.admins.add("Iair");
        User.admins.add("Shoshana");
        User.admins.add("Tohar");
        User.admins.add("Samuel");
        User.admins.add("Mor");

        Thread th = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    in = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(in);
                }
                finish();
            }
        };
        th.start();

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

    private void setAllTeams() {
        User.teams.put(1574, "MisCar");
        User.teams.put(1576, "Voltrix");
        User.teams.put(1580, "The Blue Monkeys");
        User.teams.put(1657, "Hamosad");
        User.teams.put(1937, "Elysium");
        User.teams.put(1942, "Cinderella Tel-Nof");
        User.teams.put(1943, "Neat Team");
        User.teams.put(1954, "ElectroBunny");
        User.teams.put(2096, "RoboActive");
        User.teams.put(3034, "Galileo");
        User.teams.put(3065, "Jatt High School");
        User.teams.put(3211, "The Y Team");
        User.teams.put(4319, "Ladies FIRST");
        User.teams.put(4320, "The Joker");
        User.teams.put(4338, "Falcons");
        User.teams.put(4586, "PRIMO");
        User.teams.put(4590, "GreenBlitz");
        User.teams.put(4661, "The Red Pirates");
        User.teams.put(4784, "ATOM");
        User.teams.put(5038, "MEGIDDO LIONS");
        User.teams.put(5135, "Black Unicorns");
        User.teams.put(5291, "Emperius");
        User.teams.put(5554, "The Poros Robotics");
        User.teams.put(5715, "DRC");
        User.teams.put(5747, "Athena");
        User.teams.put(5928, "MetalBoost");
        User.teams.put(5990, "TRIGON");
        User.teams.put(6168, "alzahrawi");
        User.teams.put(6230, "Team Koi");
        User.teams.put(6738, "Excalibur");
        User.teams.put(6740, "G3 - Glue Gun & Glitter");
        User.teams.put(7067, "Team Streak");
        User.teams.put(7112, "EverGreen");
        User.teams.put(7554, "Green Rockets");
        User.teams.put(7845, "8BIT");
        User.teams.put(8175, "Piece of Mind");
        User.teams.put(1577, "Steampunk");
        User.teams.put(1690, "Orbit");
        User.teams.put(2212, "The Spikes");
        User.teams.put(2230, "General Angels");
        User.teams.put(2231, "OnyxTronix");
        User.teams.put(2630, "Thunderbolts");
        User.teams.put(2679, "Tiger Team");
        User.teams.put(3075, "Ha-Dream Team");
        User.teams.put(3083, "Artemis");
        User.teams.put(3316, "D-Bug");
        User.teams.put(3339, "BumbleB");
        User.teams.put(3388, "Flash");
        User.teams.put(3835, "Vulcan");
        User.teams.put(4416, "Skynet");
        User.teams.put(4744, "Ninjas");
        User.teams.put(5614, "Team Sycamore");
        User.teams.put(5635, "Demacia");
        User.teams.put(5654, "Phoenix");
        User.teams.put(5951, "Makers Assemble");
        User.teams.put(5987, "Galaxia in memory of David Zohar");
        User.teams.put(6104, "Desert Eagles");
        User.teams.put(7039, "XO");
        User.teams.put(7079, "Co-Bot");
        User.teams.put(8223, "Mariners");
        User.teams.put(8333, "TOPAZ4X4");
        User.teams.put(8340, "F.R.A.Y");
        User.teams.put(8356, "Black Rhinos");
    }
}
