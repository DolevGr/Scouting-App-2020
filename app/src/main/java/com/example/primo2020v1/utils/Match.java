package com.example.primo2020v1.utils;

import android.graphics.Color;

import java.io.Serializable;

public class Match implements Serializable {
    public String fRed, mRed, cRed,
            fBlue, mBlue, cBlue;
    private int gameNum;

    public Match(String r1, String r2, String r3, String b1, String b2, String b3, int game) {
        cRed = r1;
        mRed = r2;
        fRed = r3;
        cBlue = b1;
        mBlue = b2;
        fBlue = b3;
        this.gameNum = game;
    }

    public Match() {
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public int getGameNum() {
        return gameNum;
    }

    public String getFirstRedRobot() {
        return cRed;
    }

    public String getSecondRedRobot() {
        return mRed;
    }

    public String getThirdRedRobot() {
        return fRed;
    }

    public String getFirstBlueRobot() {
        return cBlue;
    }

    public String getSecondBlueRobot() {
        return mBlue;
    }

    public String getThirdBlueRobot() {
        return fBlue;
    }

    public Match copy() {
        return new Match(cRed, mRed, fRed,
                cBlue, mBlue, fBlue,
                gameNum);
    }

    public boolean hasTeam(String teamNumber) {
        return cRed.equals(teamNumber) || mRed.equals(teamNumber) || fRed.equals(teamNumber) ||
                cBlue.equals(teamNumber) || mBlue.equals(teamNumber) || fBlue.equals(teamNumber);
    }

    public int teamColor(String team) {
        if (cRed.equals(team) || mRed.equals(team) || fRed.equals(team))
            return Color.RED;
        else if (cBlue.equals(team) || mBlue.equals(team) || fBlue.equals(team))
            return Color.BLUE;
        return Color.BLACK;
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
                ", GameNumber'" + gameNum + '\'' +
                '}';
    }
}
