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


    public String name, password;
    public boolean privilege;

    public User(String name, String pass){
        this.name = name;
        this.password = pass;
        this.privilege = false;
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

    public boolean isPrivilege() {
        return privilege;
    }

    public void setPrivilege() {
        this.privilege = true;
    }
}
