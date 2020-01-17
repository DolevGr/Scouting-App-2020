package com.example.primo2020v1.libs;

import androidx.annotation.NonNull;

public class Cycle {
    private final int OPTIONS = 4;

    //true: Tele; false: Auto
    private int[] pc;
    private boolean phase;

    public Cycle(int pcMissed, int pcLower, int pcOuter, int pcInner, boolean phase){
        this.phase = phase;
        pc = new int[OPTIONS];

        pc[0] = pcMissed;
        pc[1] = pcLower;
        pc[2] = pcOuter;
        pc[3] = pcInner;
    }

    public void setCycle(int[] newCycle){
        for (int i = 0; i < newCycle.length; i++)
            pc[i] = newCycle[i];
    }

    public int[] getCycle(){
        return pc;
    }

    public boolean getPhase() {
        return phase;
    }

    @NonNull
    @Override
    public String toString() {
        String state;
        if(phase)
            state = "Tele";
        else
            state = "Auto";

        return "Cycle[" + state +
                ": Missed: " + pc[0] +
                ", Lower: " + pc[1] +
                ", Outer: " + pc[2] +
                ", Inner: " + pc[3] +
                " ]";
    }
}
