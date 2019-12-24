package com.example.primo2020v1.libs;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static int NUMBER_OF_MATCHES = 80;
    public static HashMap<Integer, String> teamsHM = new HashMap<Integer, String>(NUMBER_OF_MATCHES);
    public static ArrayList<Match> matches;
    public static int currentGame = 1;
    public static ArrayList<String> Members = new ArrayList<>();


    public String name, password;

    public User(String name, String pass){
        this.name = name;
        this.password = pass;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
