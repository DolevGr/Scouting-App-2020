package com.example.primo2020v1.libs;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static final int NUMBER_OF_TEAMS = 80;
    public static HashMap<Integer, String> teamsHM = new HashMap<Integer, String>(NUMBER_OF_TEAMS);
    public static ArrayList<Match> matches;
    public static int currentGame = 1;
}
