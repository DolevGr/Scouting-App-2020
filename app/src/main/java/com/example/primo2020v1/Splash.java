package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Splash extends AppCompatActivity {
    private static final String TAG = "Splash";

    Intent in;
    boolean addToFireBase = false;
    int usersNumber, matchesNumber;
    private DatabaseReference dbRefUsers, dbRefMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersNumber = Integer.parseInt(dataSnapshot.child("UsersNumber").getValue().toString());
                matchesNumber = Integer.parseInt(dataSnapshot.child("MatchesNumber").getValue().toString());
                Log.d(TAG, "onDataChange: " + usersNumber + ", " + matchesNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        dbRefMatches = User.databaseReference.child(Keys.MATCHES);
        dbRefUsers = User.databaseReference.child(Keys.USERS);
        for (int i = 0; i < usersNumber; i++) {
            final int finalI = i;
            dbRefUsers = dbRefUsers.child(Integer.toString(finalI));
            dbRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        User.members.add(dataSnapshot.child("name").getValue().toString());
                    } catch (Exception e) {
                        Log.d(TAG, "onDataChange: Users");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            Log.d(TAG, "onDataChange: " + User.members.get(i));
        }

        for (int i = 0; i < matchesNumber; i++) {
            final int finalI = i;
            dbRefMatches = dbRefMatches.child(Integer.toString(finalI));
            dbRefMatches.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        User.matches.add(dataSnapshot.getValue(Match.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            Log.d(TAG, "onCreate: Users: " + User.members.toString() + "\nMatches: " + User.matches.toString());
        }


//        User.teams.add("4586 1");
//        User.teams.add("4586 2");
//        User.teams.add("4586 3");
//        User.teams.add("4586 4");
//        User.teams.add("4586 5");
//        User.teams.add("4586 6");

        User.admins.add("Dolev");
        User.admins.add("Iair");
        User.admins.add("Shoshana");
        User.admins.add("Tohar");
        User.admins.add("Samuel");
        User.admins.add("Mor");

        if(addToFireBase)
            addToDatabase();
        GeneralFunctions.setCurrentGame();

        Thread th = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    in = new Intent(Splash.this, LoginActivity.class);
                    startActivity(in);
                }
                finish();
            }
        };
        th.start();
    }

    //This function is for debugging only
    public void addToDatabase() {
        //Adds matches to Firebase
        for(int i = 1; i < User.matches.size() + 1; i++) {
            Map<String, Object> match = GeneralFunctions.getMap(User.matches.get(i-1));
            User.databaseReference.child(Keys.MATCHES).child(Integer.toString(i)).setValue(match);
        }

        //Adds users to Firebase
        if (false){ // Never add/remove users with code
            int j = 0;
            for (int i = 0; i < User.members.size(); i++) {
                User u = new User(User.members.get(i),"Test");

                if(j < User.admins.size() && User.members.get(i).equals(User.admins.get(j))) {
                    u.setPrivilege();
                    j++;
                }

                Map<String, Object> user = GeneralFunctions.getMap(u);
                User.databaseReference.child(Keys.USERS).child(Integer.toString(i)).setValue(user);
            }
        }

    }
}
