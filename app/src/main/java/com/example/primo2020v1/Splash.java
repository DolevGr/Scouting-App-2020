package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Map;

public class Splash extends AppCompatActivity {

    Intent in;
    boolean addToFirebase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int usersNumber = 44;
        DatabaseReference dbRef = User.databaseReference.child("Users");

        for (int i = 0; i < usersNumber; i++){
            final int finalI = i;
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        User.members.add(dataSnapshot.child(Integer.toString(finalI)).child("name").getValue().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        User.teams.add("4586 1");
        User.teams.add("4586 2");
        User.teams.add("4586 3");
        User.teams.add("4586 4");
        User.teams.add("4586 5");
        User.teams.add("4586 6");

        User.admins.add("Dolev");
        User.admins.add("Iair");
        User.admins.add("Shoshana");
        User.admins.add("Tohar");
        User.admins.add("Samuel");
        User.admins.add("Mor");

        Collections.sort(User.members);
        Collections.sort(User.admins);

        for(int i = 1; i < User.NUMBER_OF_MATCHES+1; i++) {
            User.matches.add( new Match(User.teams.get(0), User.teams.get(1), User.teams.get(2),
                    User.teams.get(3), User.teams.get(4), User.teams.get(5), i));
        }

        Log.d("Matches ", "onCreate: " + User.matches.get(0).toString());

        if(addToFirebase)
            addToDatabase();


        Thread th = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    Log.d("Splash:", "go to LOGIN *********************************************");
                    in = new Intent(Splash.this, LoginActivity.class);
                    startActivity(in);
                }
                finish();
            }
        };
        th.start();
    }

    //This function is for debugging only
    public void addToDatabase(){
        //Adds matches to Firebase
        for(int i = 1; i < User.matches.size() + 1; i++) {
            Map<String, Object> match = GeneralFunctions.getMap(User.matches.get(i-1));
            User.databaseReference.child("Match").child(Integer.toString(i)).setValue(match);
        }

        //Adds users to Firebase
        int j = 0;
        for(int i = 0; i < User.members.size(); i++){
            User u = new User(User.members.get(i),"Test");

            if(j < User.admins.size() && User.members.get(i).equals(User.admins.get(j))) {
                u.setPrivilege();
                j++;
            }

            Map<String, Object> user = GeneralFunctions.getMap(u);
            User.databaseReference.child("Users").child(Integer.toString(i)).setValue(user);
        }

        //Adds teams to Firebase
        //No need to setValue()
//        for(int i = 0; i < User.teams.size(); i++) {
//            User.databaseReference.child("Teams").child(Integer.toString(i)).setValue(User.teams.get(i));
//        }
//
//        User.databaseReference.child("CurrentGame").setValue(User.currentGame);
    }
}
