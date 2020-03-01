package com.example.primo2020v1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.utils.GeneralFunctions;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.Match;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private Intent in;
    private String lastUpdated, lastUpdatedDB, appVersion, versionDB;

    public static Intent gameService;
    private boolean mIsBound = false;
    private ScoutingService gServ;
    private ServiceConnection Scon = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            gServ = ((ScoutingService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            gServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        User.masterRanks.add("Coach");
        User.masterRanks.add("Dolev");

        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.formMatch = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString().trim());
                lastUpdatedDB = dataSnapshot.child(Keys.LAST_UPDATED).getValue().toString().trim();
                versionDB = dataSnapshot.child("Version").getValue().toString().trim();

                appVersion = getResources().getString(R.string.app_name);
                Log.d(TAG, "onDataChange: " + versionDB + " : " + getResources().getString(R.string.app_name));
                if (!appVersion.contains(versionDB)) {
                    new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("Version " + appVersion + " Is Incorrect")
                            .setMessage("The current version of the app is " + versionDB + ".\nPlease update your application to continue.")
                            .setPositiveButton("Ok", (dialog, which) -> finish())
                            .show();
                } else {
//                    setUsersNames(dataSnapshot);
//                    setMatches(dataSnapshot);
//                    setParticipants(dataSnapshot);

                    gameService = new Intent();
                    gServ = new ScoutingService();
                    gameService.setClass(SplashActivity.this, ScoutingService.class);
                    startService(gameService);

                    in = new Intent(SplashActivity.this, LoginActivity.class);

                    Map<String, String> infoMap = GeneralFunctions.readFromFile("Info.txt", getApplicationContext());

                    String name = infoMap.get("Name");
                    String password = infoMap.get("Password");
                    String rank = infoMap.get("Rank");
                    String lastUpdated = infoMap.get("Date");

                    if (lastUpdated == null || !lastUpdated.equals(lastUpdatedDB)) {

                        infoMap.put("Date", lastUpdatedDB);
                        GeneralFunctions.writeToFile("Info.txt", getApplicationContext(), infoMap);

                        setParticipants(dataSnapshot);
                        GeneralFunctions.writeToFile("Participants.txt", getApplicationContext(), User.participants);

                        setMatches(dataSnapshot);
                        GeneralFunctions.writeObjectToFile("Matches.ser", getApplicationContext(), User.matches);

                    } else {
                        Map<String, String> map = GeneralFunctions.readFromFile("Participants.txt", getApplicationContext());
                        if (map.isEmpty()) {
                            setParticipants(dataSnapshot);
                        } else {
                            for (Map.Entry<String, String> ent : map.entrySet()) {
                                User.participants.put(Integer.parseInt(ent.getKey()), ent.getValue());
                            }
                        }

                        User.matches = (ArrayList<Match>) GeneralFunctions.readObjectFromFile("Matches.ser", getApplicationContext());
                    }

                    if (User.participants.isEmpty()) {
                        setParticipants(dataSnapshot);
                    }

//                    setUsersNames(dataSnapshot);

                    if (User.matches == null || User.matches.isEmpty()) {
                        setMatches(dataSnapshot);
                        Log.d(TAG, "onDataChange: Dead");
                    }

                    if (name != null && password != null && rank != null) {

                        in = new Intent(SplashActivity.this, DrawerActivity.class);
                        in.putExtra("Username", name);
                        in.putExtra("Rank", rank);
                    }


                    startActivity(in);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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

    private void setParticipants(DataSnapshot dataSnapshot) {
        DataSnapshot dsParticipants = dataSnapshot.child("Participants");

        for (DataSnapshot ds : dsParticipants.getChildren()) {
            User.participants.put(Integer.parseInt(ds.getKey()), ds.getValue().toString());
        }
    }
}
