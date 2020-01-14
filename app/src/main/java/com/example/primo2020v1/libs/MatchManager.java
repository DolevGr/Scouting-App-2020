package com.example.primo2020v1.libs;

import android.util.Log;
import android.widget.SeekBar;

import com.example.primo2020v1.GameFormActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Integer> tele = new HashMap<>(),
                auto = new HashMap<>();
        tele.put("TeleMissed", pcTele[0]);
        tele.put("TeleLower", pcTele[1]);
        tele.put("TeleOuter", pcTele[2]);
        tele.put("TeleInner", pcTele[3]);

        auto.put("AutoMissed", pcTele[0]);
        auto.put("AutoLower", pcTele[1]);
        auto.put("AutoOuter", pcTele[2]);
        auto.put("AutoInner", pcTele[3]);

        powerCellsReff.child("PowerCellsTele").child("Cycle" + cycle).setValue(tele);
        powerCellsReff.child("PowerCellsTele").child("Total Power Cells").setValue(totalTele);

        powerCellsReff.child("PowerCellsAuto").child("Cycle" + cycle).setValue(auto);
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


    //Power Cells SeekBars logic
    private int getPowerCellsFromSeekBars(){
        return gameFormActivity.sbPowerCellsMissed.getProgress()
                + gameFormActivity.sbPowerCellsLower.getProgress()
                + gameFormActivity.sbPowerCellsOuter.getProgress()
                + gameFormActivity.sbPowerCellsInner.getProgress();
    }

    public int getMax(SeekBar sb){
        return 5 - getPowerCellsFromSeekBars() + sb.getProgress();
    }

    public void setMaxSeekBars(){
        gameFormActivity.sbPowerCellsMissed.setMax(5);
        gameFormActivity.sbPowerCellsLower.setMax(5);
        gameFormActivity.sbPowerCellsOuter.setMax(5);
        gameFormActivity.sbPowerCellsInner.setMax(5);
    }

    public void updateSeekBarsText(){
        gameFormActivity.tvPowerCellsMissed.setText(Integer.toString(gameFormActivity.sbPowerCellsMissed.getProgress()));
        gameFormActivity.tvPowerCellsLower.setText(Integer.toString(gameFormActivity.sbPowerCellsLower.getProgress()));
        gameFormActivity.tvPowerCellsOuter.setText(Integer.toString(gameFormActivity.sbPowerCellsOuter.getProgress()));
        gameFormActivity.tvPowerCellsInner.setText(Integer.toString(gameFormActivity.sbPowerCellsInner.getProgress()));
    }
}
