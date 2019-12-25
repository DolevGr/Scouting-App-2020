package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Splash extends AppCompatActivity {

    Intent in;

    boolean addToFirebase = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        User.matches = new ArrayList<>();

//        User.teamsHM.put(4586, "Primo");
//        User.teamsHM.put(1574, "Miscar");
//        User.teamsHM.put(1580, "The Blue Monkeys");
//        User.teamsHM.put(1576, "Voltrix");
//        User.teamsHM.put(1657, "Hamosad");
//        User.teamsHM.put(1937, "Elysium");
//        User.teamsHM.put(1942, "Cinderella");
//        User.teamsHM.put(1943, "Neat Team");

        User.members.add("Dolev");
        User.members.add("Tal");
        User.members.add("Itay");
        User.members.add("Tohar");
        User.members.add("Adi");
        User.members.add("Samuel");
        User.members.add("Chen A");
        User.members.add("Chen P");
        User.members.add("Iair");
        User.members.add("Yoav");
        User.members.add("Yuval");
        User.members.add("Yonatan");
        User.members.add("Yael");
        User.members.add("Yarin");
        User.members.add("Lior");
        User.members.add("Liora");
        User.members.add("Maor");
        User.members.add("Sivan");
        User.members.add("Idan");
        User.members.add("Keren");
        User.members.add("Shoshana");
        User.members.add("Shahar V");
        User.members.add("Tamar");
        User.members.add("Tom");
        User.members.add("Inbar");
        User.members.add("Omer");
        User.members.add("Amit");
        User.members.add("Yoni");
        User.members.add("Rotem");
        User.members.add("Shira");
        User.members.add("Sagi");
        User.members.add("Aryeh");
        User.members.add("Racheli");
        User.members.add("Rony");
        User.members.add("Yaniv");
        User.members.add("Gilad");
        User.members.add("Shahar O");
        User.members.add("Hila");
        User.members.add("Amir D");
        User.members.add("Amir G");
        User.members.add("Liad");
        User.members.add("Niv");
        User.members.add("Noam");
        User.members.add("Peleg");
        Collections.sort(User.members);

        for(int i = 1; i < User.NUMBER_OF_MATCHES+1; i++) {
            User.matches.add(new Match("R Close "+i, "R Middle "+i, "R Far "+i,
                                    "B Close "+i, "B Middle "+i, "B Far "+i, i));
        }
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
            }
        };
        th.start();
    }

    public void addToDatabase(){
        if(addToFirebase){
            for(int i = 0; i < User.NUMBER_OF_MATCHES; i++){
                if (i < User.members.size()) {
                    User u = new User(User.members.get(i), "Test");
                    User.databaseReference.child("Users").child(Integer.toString(i+1)).setValue(u);
                }

                Map<String, Object> m = GeneralFunctions.getMap(User.matches.get(i));
                User.databaseReference.child("Match").child(Integer.toString(i+1)).setValue(m);
            }
        }
    }
}
