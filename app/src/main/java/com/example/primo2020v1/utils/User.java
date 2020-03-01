package com.example.primo2020v1.utils;

import com.example.primo2020v1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static HashMap<Integer, String> participants = new HashMap<>();
    public static ArrayList<Match> matches = new ArrayList<>();
    public static ArrayList<String> masterRanks = new ArrayList<>();
    public static int formMatch = 1;
    public static int liveMatch = 1;
    public static boolean hasAlerted = false;
    public static String username = "Scouter";
    public static String userRank = "Scouter";

    public static final int[] controlPanelRotation = {R.drawable.ic_cprc, R.drawable.ic_cprc_selected};
    public static final int[] controlPanelPosition= {R.drawable.ic_cppc, R.drawable.ic_cppc_selected};
    public static final int[] endGameImages = {R.drawable.ic_empty, R.drawable.ic_park, R.drawable.ic_climb, R.drawable.ic_balanced};
    public static final int[] finishImages = {R.drawable.ic_won, R.drawable.ic_lost, R.drawable.ic_draw};
    public static final int[] finishTickets = {R.drawable.ic_no_foul, R.drawable.ic_yellow_foul, R.drawable.ic_red_foul};
    public static final int[] finishCrash = {R.drawable.ic_no_crash, R.drawable.ic_crash};
    public static final int[] finishDefence = {R.drawable.ic_no_defence, R.drawable.ic_defence};


    public String name, password, rank;

    public User(String name, String pass){
        this.name = name;
        this.password = pass;
        this.rank = "Scouter";
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
