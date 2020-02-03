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
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class Splash extends AppCompatActivity {
    private static final String TAG = "Splash";

    Intent in;
    boolean addToFireBase = false, isFirstEntry = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        User.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.currentGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
                Log.d(TAG, "onDataChange: " + User.currentGame);

                if (isFirstEntry) {
                    DataSnapshot dsUsers = dataSnapshot.child(Keys.USERS),
                            dsMatches = dataSnapshot.child(Keys.MATCHES);
//                setUsersNames(dsUsers);
//                setMatches(dsMatches);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


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

    private void setMatches(DataSnapshot dataSnapshot) {
        int i = 1;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User.matches.add(Objects.requireNonNull(ds.child(Integer.toString(i)).getValue(Match.class)));
            i++;
        }
        Log.d(TAG, "setMatches: " + User.matches.toString());
    }

    private void setUsersNames(DataSnapshot dataSnapshot) {
        int i = 0;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User u = new User();
            u.setName(ds.child(Integer.toString(i)).getValue(User.class).getName());
            Log.d(TAG, "setUsersNames: " + u.getName());
            User.members.add(u.getName());
            i++;
        }
        Log.d(TAG, "setUsersNames: " + User.members.toString());
    }


    //This function is for debugging only
    public void addToDatabase() {
        //Adds matches to Firebase
        for(int i = 1; i < User.matches.size() + 1; i++) {
            Map<String, Object> match = GeneralFunctions.getMap(User.matches.get(i-1));
            User.databaseReference.child(Keys.MATCHES).child(Integer.toString(i)).setValue(match);
        }

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
