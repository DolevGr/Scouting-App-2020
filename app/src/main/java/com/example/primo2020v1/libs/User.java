package com.example.primo2020v1.libs;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class User {
    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static final int NUMBER_OF_MATCHES = 80;
    public static ArrayList<String> teams = new ArrayList<>();
    public static ArrayList<Match> matches = new ArrayList<>();
    public static int currentGame = 1;
    public static ArrayList<String> members = new ArrayList<>();
    public static ArrayList<String> admins = new ArrayList<>();
    public static int score = 0,
            ppTeleBottom = 0, ppTeleOuter = 0, ppTeleInner = 0,
            ppAutoBottom = 0, ppAutoOuter = 0, ppAutoInner = 0,
            pcRotations = 0, numOfHangedRobots = 0, RP = 0;
    public static boolean movedFromInitLine = false, didHang = false;
    public static String textBox = "";


    public String name, password;
    public boolean privillege;

    public User(String name, String pass){
        this.name = name;
        this.password = pass;
        this.privillege = false;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivillege() {
        return privillege;
    }

    public void setPrivillege() {
        this.privillege = true;
    }
}
