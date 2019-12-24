package com.example.primo2020v1.libs;

import androidx.annotation.NonNull;

public class Match{
    private Allience Red, Blue;
    private int gameNum;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game){
        Red = new Allience(r1, r2, r3);
        Blue = new Allience(b1, b2, b3);

        this.gameNum = game;
    }

    public Allience getRedTeam(){
        return this.Red;
    }

    public Allience getBlueTeam(){
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
