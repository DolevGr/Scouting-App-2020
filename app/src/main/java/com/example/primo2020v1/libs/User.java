package com.example.primo2020v1.libs;

import android.graphics.Color;

import com.example.primo2020v1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static HashMap<Integer, String> teams = new HashMap<>();
    public static ArrayList<Match> matches = new ArrayList<>();
    public static ArrayList<String> members = new ArrayList<>();
    public static int currentGame = 1;
    public static ArrayList<String> admins = new ArrayList<>();
    public static String username = "Scouter";

    public static final int[] controlPanelRotation = {R.drawable.cp_rc, R.drawable.cp_rc_selected};
    public static final int[] controlPanelPosition= {R.drawable.cp_pc, R.drawable.cp_pc_selected};
    public static final int[] endGameImages = {R.drawable.ic_empty, R.drawable.ic_park, R.drawable.ic_climb, R.drawable.ic_balance};
    public static final int[] finishImages = {R.drawable.ic_won, R.drawable.ic_lost, R.drawable.ic_draw};
    public static final int[] finishTickets = {Color.BLACK, Color.YELLOW, Color.RED};
    public static final int[] finishCrash = {Color.RED, Color.GREEN};
    public static final int[] finishDefence = {Color.RED, Color.GREEN};


    public String name, password;
    public boolean privilege;

    public User(String name, String pass){
        this.name = name;
        this.password = pass;
        this.privilege = false;
    }

    public User() { }

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

    public void setPrivilege(boolean b) {
        this.privilege = b;
    }
}
