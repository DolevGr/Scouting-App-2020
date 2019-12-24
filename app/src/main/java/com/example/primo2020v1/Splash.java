package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class Splash extends AppCompatActivity {
    DatabaseReference databaseReference;

    Intent in;

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

        User.Members.add("Dolev");
        User.Members.add("Tal");
        User.Members.add("Itay");
        User.Members.add("Tohar");
        User.Members.add("Adi");
        User.Members.add("Samuel");
        User.Members.add("Chen A");
        User.Members.add("Chen P");
        User.Members.add("Yair");
        User.Members.add("Yoav");
        User.Members.add("Yuval");
        User.Members.add("Yonatan");
        User.Members.add("Yael");
        User.Members.add("Yarin");
        User.Members.add("Lior");
        User.Members.add("Liora");
        User.Members.add("Maor");
        User.Members.add("Sivan");
        User.Members.add("Idan");
        User.Members.add("Keren");
        User.Members.add("Shoshana");
        User.Members.add("Shahar V");
        User.Members.add("Tamar");
        User.Members.add("Tom");
        User.Members.add("Inbar");
        User.Members.add("Omer");
        User.Members.add("Amit");
        User.Members.add("Yoni");
        User.Members.add("Rotem");
        User.Members.add("Shira");
        User.Members.add("Sagi");
        User.Members.add("Aryeh");
        User.Members.add("Racheli");
        User.Members.add("Rony");
        User.Members.add("Yaniv");
        User.Members.add("Gilad");
        User.Members.add("Shahar O");
        User.Members.add("Hila");
        User.Members.add("Amir D");
        User.Members.add("Amir G");
        User.Members.add("Liad");
        User.Members.add("Niv");
        User.Members.add("Noam");
        User.Members.add("Peleg");
        Collections.sort(User.Members);

        for(int i = 1; i < User.NUMBER_OF_MATCHES+1; i++) {
            User.matches.add(new Match("R Front "+i, "R Center "+i, "R Back "+i,
                                    "B Front "+i, "B Center "+i, "B Back "+i, i));
        }

        //Adding data to Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(false){
            for(int i = 1; i < User.NUMBER_OF_MATCHES+1; i++){
                if (i < User.Members.size()+1) {
                    databaseReference.child("Users").child(Integer.toString(i)).setValue(User.Members.get(i - 1));
                }

                String str = User.matches.get(i-1).toString();
                databaseReference.child("Match").child(Integer.toString(i)).setValue(str);
            }
        }



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
}
