package com.example.primo2020v1.libs;

import android.util.Log;

import com.example.primo2020v1.GameFormActivity;
import com.google.firebase.database.DatabaseReference;

import static android.content.ContentValues.TAG;

public class MatchManager {
    DatabaseReference powerCellsReff;

    private String teamName, position;
    private int gameNumber, totalTele, totalAuto;
    private GameFormActivity gameFormActivity;

    //index 0: Missed; 1: Lower; 2: Outer; 3: Inner
    private int[] pcTele = new int[4];
    private int[] pcAuto = new int[4];

    public MatchManager(String teamName, String position, int gameNumber, GameFormActivity gameFormActivity){
        this.teamName = teamName;
        this.position = position;
        this.gameNumber = gameNumber;
        this.gameFormActivity = gameFormActivity;

        totalAuto = 0;
        totalTele = 0;
        for(int i = 0; i < pcAuto.length; i++) {
            pcAuto[i] = 0;
            pcTele[i] = 0;
        }

        convertPositionAsInDB();
        powerCellsReff = User.databaseReference.child("Matches").child(Integer.toString(this.gameNumber)).child(this.position);
    }

    //Tele: true; Auto: false
    public void endOfCycle(boolean phase){
        if(phase){
            pcTele[0] = gameFormActivity.sbPowerCellsMissed.getProgress();
            pcTele[1] = gameFormActivity.sbPowerCellsLower.getProgress();
            pcTele[2] = gameFormActivity.sbPowerCellsOuter.getProgress();
            pcTele[3] = gameFormActivity.sbPowerCellsInner.getProgress();

            for (int i = 0; i < pcTele.length; i++)
                totalTele += pcTele[i];

        } else {
            pcAuto[0] = gameFormActivity.sbPowerCellsMissed.getProgress();
            pcAuto[1] = gameFormActivity.sbPowerCellsLower.getProgress();
            pcAuto[2] = gameFormActivity.sbPowerCellsOuter.getProgress();
            pcAuto[3] = gameFormActivity.sbPowerCellsInner.getProgress();

            for (int i = 0; i < pcAuto.length; i++)
                totalAuto += pcAuto[i];
        }
    }

    public void resetSeekBars(){
        gameFormActivity.sbPowerCellsMissed.setProgress(0);
        gameFormActivity.sbPowerCellsLower.setProgress(0);
        gameFormActivity.sbPowerCellsOuter.setProgress(0);
        gameFormActivity.sbPowerCellsInner.setProgress(0);
    }

    public void addCyclesToDB(int cycle){
        powerCellsReff.child("PowerCellsTele").child("Cycle" + cycle).setValue(pcTele[0]);
        powerCellsReff.child("PowerCellsTele").child("Cycle" + cycle).setValue(pcTele[1]);
        powerCellsReff.child("PowerCellsTele").child("Cycle" + cycle).setValue(pcTele[2]);
        powerCellsReff.child("PowerCellsTele").child("Cycle" + cycle).setValue(pcTele[3]);
        powerCellsReff.child("PowerCellsTele").child("Total Power Cells").setValue(totalTele);

        powerCellsReff.child("PowerCellsAuto").child("Cycle" + cycle).setValue(pcAuto[0]);
        powerCellsReff.child("PowerCellsAuto").child("Cycle" + cycle).setValue(pcAuto[1]);
        powerCellsReff.child("PowerCellsAuto").child("Cycle" + cycle).setValue(pcAuto[2]);
        powerCellsReff.child("PowerCellsAuto").child("Cycle" + cycle).setValue(pcAuto[3]);
        powerCellsReff.child("PowerCellsAuto").child("Total Power Cells").setValue(totalAuto);

        Log.d(TAG, "addCyclesToDB: beep bloop adding cycles");
    }

    private void convertPositionAsInDB(){
        switch (this.position){
            case "R Close":
                this.position = "cRed";
                break;
            case "R Middle":
                this.position = "mRed";
                break;
            case "R Far":
                this.position = "fRed";
                break;
            case "B Close":
                this.position = "cBlue";
                break;
            case "b Middle":
                this.position = "mBlue";
                break;
            case "b Far":
                this.position = "fBlue";
                break;
        }

        Log.d(TAG, "renamePositionAsInDB: " + this.position);
    }
}
