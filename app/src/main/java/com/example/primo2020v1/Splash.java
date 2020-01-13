package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;

import java.util.Collections;
import java.util.Map;

public class Splash extends AppCompatActivity {

    Intent in;
    boolean addToFirebase = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        User.teams.add("4586 1");
        User.teams.add("4586 2");
        User.teams.add("4586 3");
        User.teams.add("4586 4");
        User.teams.add("4586 5");
        User.teams.add("4586 6");

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
        User.members.add("Yarin");
        User.members.add("Lior");
        User.members.add("Liora");
        User.members.add("Maor");
        User.members.add("Mor");
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

        User.admins.add("Dolev");
        User.admins.add("Iair");
        User.admins.add("Shoshana");
        User.admins.add("Tohar");
        User.admins.add("Samuel");
        User.admins.add("Mor");
        Collections.sort(User.members);

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
        for(int i = 1; i < User.matches.size() + 1; i++){
            Map<String, Object> match = GeneralFunctions.getMap(User.matches.get(i-1));
            User.databaseReference.child("Match").child(Integer.toString(i)).setValue(match);
        }

        //Adds users to Firebase
        for(int i = 0; i < User.members.size()-1; i++){
            User u = new User(User.members.get(i),"Test");
            if(User.admins.contains(User.members.get(i)))
                u.setPrivilege();

            Map<String, Object> user = GeneralFunctions.getMap(u);
            User.databaseReference.child("Users").child(Integer.toString(i)).setValue(user);
        }

        //Adds teams to Firebase
        for(int i = 0; i < User.teams.size(); i++) {
            User.databaseReference.child("Teams").child(Integer.toString(i)).setValue(User.teams.get(i));
        }

        User.databaseReference.child("CurrentGame").setValue(User.currentGame);
    }
}
