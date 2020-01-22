package com.example.primo2020v1.libs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cycle implements Parcelable {
    private final int OPTIONS = 4;

    //true: Tele; false: Auto
    public int[] pc;
    public boolean phase;

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

    public int getTotalPC(){
        int sum = 0;
        for(int i = 0; i < pc.length; i++)
            sum += pc[i];

        return sum;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(pc);
        parcel.writeByte((byte) (phase ? 1 : 0));
    }

    protected Cycle(Parcel in) {
        pc = in.createIntArray();
        phase = in.readByte() != 0;
    }

    public static final Creator<Cycle> CREATOR = new Creator<Cycle>() {
        @Override
        public Cycle createFromParcel(Parcel in) {
            return new Cycle(in);
        }

        @Override
        public Cycle[] newArray(int size) {
            return new Cycle[size];
        }
    };
}
