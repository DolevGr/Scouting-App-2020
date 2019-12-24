package com.example.primo2020v1.libs;

import androidx.annotation.NonNull;

public class Match{
    public Alliance Red, Blue;
    private int gameNum;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game){
        Red = new Alliance(r1, r2, r3);
        Blue = new Alliance(b1, b2, b3);

        this.gameNum = game;
    }

    public Alliance getRedTeam(){
        return this.Red;
    }

    public Alliance getBlueTeam(){
        return this.Blue;
    }

    public int getGameNum() {
        return gameNum;
    }

    @NonNull
    @Override
    public String toString() {
        return Red.toString() + " " + Blue.toString();
    }
}
