package com.example.primo2020v1.libs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FormInfo implements Parcelable {
    //private ArrayList<Cycle> cycles;
    public int endGame, finish, gameNumber;
    public boolean controlPanelColor, controlPanel;
    public CharSequence text;
    public String teamNumber;

    public FormInfo(/*ArrayList<Cycle> c,*/String teamNumber, int gameNumber, boolean controlPanel, boolean controlPanelColor, int endGame, int finish, CharSequence text){
//        cycles = new ArrayList<>();
//        boolean b = cycles.addAll(c);

        //Log.d(TAG, "FormInfo: " + b);

        this.gameNumber = gameNumber;
        this.teamNumber = teamNumber;
        this.controlPanel = controlPanel;
        this.controlPanelColor = controlPanelColor;
        this.endGame = endGame;
        this.finish = finish;
        this.text = text;
    }

//    public ArrayList<Cycle> getCyclesFinish() {
//        return cycles;
//    }
//
//    public void setCycles(ArrayList<Cycle> cycles) {
//        this.cycles = cycles;
//    }

    protected FormInfo(Parcel in) {
        teamNumber = in.readString();
        endGame = in.readInt();
        finish = in.readInt();
        controlPanelColor = in.readByte() != 0;
        controlPanel = in.readByte() != 0;
    }

    public static final Creator<FormInfo> CREATOR = new Creator<FormInfo>() {
        @Override
        public FormInfo createFromParcel(Parcel in) {
            return new FormInfo(in);
        }

        @Override
        public FormInfo[] newArray(int size) {
            return new FormInfo[size];
        }
    };

    public int getEndGame() {
        return endGame;
    }

    public void setEndGame(int endGame) {
        this.endGame = endGame;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public boolean isControlPanelColor() {
        return controlPanelColor;
    }

    public void setControlPanelColor(boolean controlPanelColor) {
        this.controlPanelColor = controlPanelColor;
    }

    public boolean isControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(boolean controlPanel) {
        this.controlPanel = controlPanel;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    @NonNull
    @Override
    public String toString() {
//        if (!cycles.isEmpty())
            return /*"Cycles: " + cycles.toString() +*/
                    "Team Number: " + teamNumber +
                    "; EndGame: " + endGame +
                    "; Finish: " + finish +
                    "; CP: " + controlPanel + ", " + controlPanelColor +
                    "; Text From Scouter: " + text +
                    "; ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(teamNumber);
        parcel.writeInt(endGame);
        parcel.writeInt(finish);
        parcel.writeByte((byte) (controlPanelColor ? 1 : 0));
        parcel.writeByte((byte) (controlPanel ? 1 : 0));
    }
}
