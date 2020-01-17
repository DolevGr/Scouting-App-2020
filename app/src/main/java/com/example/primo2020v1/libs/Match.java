package com.example.primo2020v1.libs;

public class Match{
    public String fRed, mRed, cRed,
            fBlue, mBlue, cBlue;
    private int gameNum;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game){
        cRed = r1; mRed = r2; fRed = r3;
        cBlue = b1; mBlue = b2; fBlue = b3;

        this.gameNum = game;
    }

    public int getGameNum() {
        return gameNum;
    }


    public String getFirstRedRobot(){
        return cRed;
    }

    public String getSecondRedRobot(){
        return mRed;
    }

    public String getThirdRedRobot(){
        return fRed;
    }

    public String getFirstBlueRobot(){
        return cBlue;
    }

    public String getSecondBlueRobot(){
        return mBlue;
    }

    public String getThirdBlueRobot(){
        return fBlue;
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
