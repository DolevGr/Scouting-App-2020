package com.example.primo2020v1.libs;

import androidx.annotation.NonNull;

public class Allience {
    private String robot1, robot2, robot3;

    public Allience(String i1, String i2, String i3){
        robot1 = i1;
        robot2 = i2;
        robot3 = i3;
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

    @NonNull
    @Override
    public String toString() {
        return robot1 + " " + robot2 + " " + robot3;
    }
}
