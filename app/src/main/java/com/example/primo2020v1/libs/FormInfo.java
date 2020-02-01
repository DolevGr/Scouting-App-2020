package com.example.primo2020v1.libs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FormInfo implements Parcelable {
    //private ArrayList<Cycle> cycles;
    public int endGame, finish, gameNumber, ticket;
    public boolean controlPanelColor, controlPanel, didCrash;
    public CharSequence text;
    public String teamNumber;

    public FormInfo(String teamNumber, int gameNumber,
                        boolean controlPanel, boolean controlPanelColor,
                        int endGame,
                        int finish, int ticket, boolean crash, CharSequence text){
        this.didCrash = crash;
        this.ticket = ticket;
        this.gameNumber = gameNumber;
        this.teamNumber = teamNumber;
        this.controlPanel = controlPanel;
        this.controlPanelColor = controlPanelColor;
        this.endGame = endGame;
        this.finish = finish;
        this.text = text;
    }

    protected FormInfo(Parcel in) {
        didCrash = in.readByte() != 0;
        ticket = in.readInt();
        text = in.readString();
        gameNumber = in.readInt();
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

    public boolean getCrash() {
        return didCrash;
    }

    public void setCrash(boolean crash) {
        this.didCrash = crash;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

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

    public CharSequence getUserComment() {
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
            return "Team Number: " + teamNumber +
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
        parcel.writeByte((byte) (didCrash ? 1 : 0));
        parcel.writeInt(ticket);
        parcel.writeString(text.toString());
        parcel.writeInt(gameNumber);
        parcel.writeString(teamNumber);
        parcel.writeInt(endGame);
        parcel.writeInt(finish);
        parcel.writeByte((byte) (controlPanelColor ? 1 : 0));
        parcel.writeByte((byte) (controlPanel ? 1 : 0));
    }
}
