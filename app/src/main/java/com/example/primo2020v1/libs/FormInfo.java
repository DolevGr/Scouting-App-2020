package com.example.primo2020v1.libs;

import android.os.Parcel;
import android.os.Parcelable;

public class FormInfo implements Parcelable {
    //private ArrayList<Cycle> cycles;
    private int gameNumber;
    private String teamNumber;

    public int endGame, finish, ticket, crash, defence;
    public boolean controlPanelColor, controlPanel;
    public CharSequence comment;

    public FormInfo(String teamNumber, int gameNumber,
                        boolean controlPanel, boolean controlPanelColor,
                        int endGame,
                        int finish, int ticket, int crash, int defence, CharSequence text){
        this.defence = defence;
        this.crash = crash;
        this.ticket = ticket;
        this.gameNumber = gameNumber;
        this.teamNumber = teamNumber;
        this.controlPanel = controlPanel;
        this.controlPanelColor = controlPanelColor;
        this.endGame = endGame;
        this.finish = finish;
        this.comment = text;
    }

    public FormInfo() { }

    protected FormInfo(Parcel in) {
        defence = in.readInt();
        crash = in.readInt();
        ticket = in.readInt();
        comment = in.readString();
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

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getCrash() {
        return crash;
    }

    public void setCrash(int crash) {
        this.crash = crash;
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
        return comment;
    }

    public void setComment(CharSequence text) {
        this.comment = text;
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

    @Override
    public String toString() {
        return "FormInfo{" +
                "endGame=" + endGame +
                ", finish=" + finish +
                ", gameNumber=" + gameNumber +
                ", ticket=" + ticket +
                ", crash=" + crash +
                ", controlPanelColor=" + controlPanelColor +
                ", controlPanel=" + controlPanel +
                ", comment=" + comment +
                ", teamNumber='" + teamNumber + '\'' +
                ", defence='" + defence + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(defence);
        parcel.writeInt(crash);
        parcel.writeInt(ticket);
        parcel.writeString(comment.toString());
        parcel.writeInt(gameNumber);
        parcel.writeString(teamNumber);
        parcel.writeInt(endGame);
        parcel.writeInt(finish);
        parcel.writeByte((byte) (controlPanelColor ? 1 : 0));
        parcel.writeByte((byte) (controlPanel ? 1 : 0));
    }
}
