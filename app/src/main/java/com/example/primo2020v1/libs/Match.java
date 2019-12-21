package com.example.primo2020v1.libs;

public class Match{
    private Allience Red, Blue;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game){
        Red = new Allience(r1, r2, r3, game);
        Blue = new Allience(b1, b2, b3, game);
    }

    public Allience getRedTeam(){
        return this.Red;
    }

    public Allience getBlueTeam(){
        return this.Blue;
    }
}
