package com.example.primo2020v1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cycle implements Parcelable {
    public int pcMissed, pcLower, pcOuter, pcInner;
    public boolean phase;

    public Cycle() {
    }

    public Cycle(int pcMissed, int pcLower, int pcOuter, int pcInner, boolean phase) {
        this.phase = phase;

        this.pcMissed = pcMissed;
        this.pcLower = pcLower;
        this.pcOuter = pcOuter;
        this.pcInner = pcInner;
    }

    public int[] toArray() {
        return new int[]{pcMissed, pcLower, pcOuter, pcInner};
    }

    public boolean getPhase() {
        return phase;
    }

    public int getTotalPC() {
        return pcLower + pcOuter + pcInner;
    }

    public int getScore() {
        int score = (1 * pcLower) + (2 * pcOuter) + (3 * pcInner);
        return phase ? score : (2 * score);
    }

    public Cycle copy() {
        return new Cycle(pcMissed, pcLower, pcOuter, pcInner, phase);
    }

    @NonNull
    @Override
    public String toString() {
        String state;
        if (phase)
            state = "Tele";
        else
            state = "Auto";

        return "Cycle[" + state +
                ": Missed: " + pcMissed +
                ", Lower: " + pcLower +
                ", Outer: " + pcOuter +
                ", Inner: " + pcInner +
                " ]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pcMissed);
        parcel.writeInt(pcLower);
        parcel.writeInt(pcOuter);
        parcel.writeInt(pcInner);
        parcel.writeByte((byte) (phase ? 1 : 0));
    }

    protected Cycle(Parcel in) {
        pcMissed = in.readInt();
        pcLower = in.readInt();
        pcOuter = in.readInt();
        pcInner = in.readInt();
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
