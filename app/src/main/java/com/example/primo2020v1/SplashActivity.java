package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    Intent in;
    String lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference(Keys.TEAMS);
//        scoresRef.keepSynced(true);

        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "LastUpdated.txt");
            file.createNewFile();
            InputStream inputStream = getApplicationContext().openFileInput("LastUpdated.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                lastUpdated = bufferedReader.readLine();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        User.masterRanks.add("Coach");
        User.masterRanks.add("Dolev");

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
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("No Internet");
                alertDialog.setMessage("Trying to connect");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                });

                alertDialog.show();
            }
        });

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
        DataSnapshot dsParticipants = dataSnapshot.child("Participants");

        for (DataSnapshot ds : dsParticipants.getChildren()) {
            User.participants.put(Integer.parseInt(ds.getKey()), ds.getValue().toString());
        }
    }
}
