package com.example.primo2020v1.libs;

import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.EndGameFragment;
import com.example.primo2020v1.Fragments.FinishFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralFunctions {

    public static Map<String,Object> getMap(Object obj) {
        Map<String,Object> map = new HashMap<>();

        try {
            Field[] fields = obj.getClass().getFields();

            for(Field f : fields) {
                if (!java.lang.reflect.Modifier.isStatic(f.getModifiers()))
                    map.put(f.getName(),f.get(obj));
            }
        } catch (Exception e){}

        return map;
    }

    public static String convertTeamFromSpinnerTODB(Match m, int index){
        switch (index){
            case 0:
                return m.getFirstRedRobot();

            case 1:
                return m.getSecondRedRobot();

            case 2:
                return m.getThirdRedRobot();

            case 3:
                return m.getFirstBlueRobot();

            case 4:
                return m.getSecondBlueRobot();

            default:
                return m.getThirdBlueRobot();
        }
    }

    public static void resetForm() {
        MatchSettingsFragment.teamNumber = "";
        MatchSettingsFragment.spnIndex = -1;
        MatchSettingsFragment.gameNumber = User.currentGame;

        PowerCellsFragment.phase = false;
        for (int i = 0; i < PowerCellsFragment.positions.length; i++) {
            PowerCellsFragment.positions[i] = 0;
        }

        ControlPanelFragment.isPCnormal = false;
        ControlPanelFragment.isPCcolor = false;
        EndGameFragment.imageIndex = 0;

        FinishFragment.finishIndex = 0;
        FinishFragment.ticketIndex = 0;
        FinishFragment.crashIndex = 0;
        FinishFragment.defIndex = 0;
        FinishFragment.text = "";
    }

    public static void onSubmit(DatabaseReference dbRef, FormInfo fi, ArrayList<Cycle> c) {
        int totalCycles = 0, totalScore = 0,
                totalPCmissed = 0, totalPClower = 0, totalPCouter = 0, totalPCinner = 0;

        if (fi.getUserComment().equals(""))
            fi.setComment("Empty Comment");
        Map<String, Object> formInfo = getMap(fi);
        dbRef.setValue(formInfo);

        dbRef.child("CommittedBy").setValue(User.username);

        if (c != null && !c.isEmpty()) {
            totalCycles = c.size();
            for (int i = 0; i <totalCycles; i++) {
                Map<String, Object> cycle = GeneralFunctions.getMap(c.get(i));
                dbRef.child("Cycle " + (i + 1)).setValue(cycle);

                totalPCmissed += c.get(i).pcMissed;
                totalPClower += c.get(i).pcLower;
                totalPCouter += c.get(i).pcOuter;
                totalPCinner += c.get(i).pcInner;
                totalScore += c.get(i).getScore();
            }

            dbRef.child("TotalMissed").setValue(totalPCmissed);
            dbRef.child("TotalLower").setValue(totalPClower);
            dbRef.child("TotalOuter").setValue(totalPCouter);
            dbRef.child("TotalInner").setValue(totalPCinner);
            dbRef.child("TotalPCScore").setValue(totalScore);
        }

        dbRef.child("TotalCycles").setValue(totalCycles);
    }
}
