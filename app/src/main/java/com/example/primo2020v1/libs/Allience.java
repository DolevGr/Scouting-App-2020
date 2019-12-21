package com.example.primo2020v1.libs;

public class Allience {
    private String robot1, robot2, robot3;
    private int gameNum;

    public Allience(String i1, String i2, String i3, int game){
        robot1 = i1;
        robot2 = i2;
        robot3 = i3;
        gameNum = game;
    }

    public String getFirstRobot(){
        return robot1;
    }

    public String getSecondRobot(){
        return robot2;
    }

    public String getThirdRobot(){
        return robot3;
    }

    public int getGameNum() {
        return gameNum;
    }
}
