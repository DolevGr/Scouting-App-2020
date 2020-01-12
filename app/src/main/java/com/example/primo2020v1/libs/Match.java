package com.example.primo2020v1.libs;

public class Match{
    public String fRed, mRed, cRed,
            fBlue, mBlue, cBlue;
    private int gameNum;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game){
        fRed = r1; mRed = r2; cRed = r3;
        fBlue = b1; mBlue = b2; cBlue = b3;

        this.gameNum = game;
    }

    public int getGameNum() {
        return gameNum;
    }


    public String getFirstRedRobot(){
        return fRed;
    }

    public String getSecondRedRobot(){
        return mRed;
    }

    public String getThirdRedRobot(){
        return cRed;
    }

    public String getFirstBlueRobot(){
        return fBlue;
    }

    public String getSecondBlueRobot(){
        return mBlue;
    }

    public String getThirdBlueRobot(){
        return cBlue;
    }

    @Override
    public String toString() {
        return "Match{" +
                "fRed='" + fRed + '\'' +
                ", mRed='" + mRed + '\'' +
                ", cRed='" + cRed + '\'' +
                ", fBlue='" + fBlue + '\'' +
                ", mBlue='" + mBlue + '\'' +
                ", cBlue='" + cBlue + '\'' +
                '}';
    }
}
