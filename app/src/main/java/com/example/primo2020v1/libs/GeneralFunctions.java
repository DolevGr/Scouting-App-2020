package com.example.primo2020v1.libs;

import com.example.primo2020v1.GameFormFragments.ControlPanelFragment;
import com.example.primo2020v1.GameFormFragments.EndGameFragment;
import com.example.primo2020v1.GameFormFragments.FinishFragment;
import com.example.primo2020v1.GameFormFragments.MatchSettingsFragment;
import com.example.primo2020v1.GameFormFragments.PowerCellsFragment;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralFunctions {

    public static Map<String, Object> getMap(Object obj) {
        Map<String, Object> map = new HashMap<>();

        try {
            Field[] fields = obj.getClass().getFields();

            for (Field f : fields) {
                if (!java.lang.reflect.Modifier.isStatic(f.getModifiers()))
                    map.put(f.getName(), f.get(obj));
            }
        } catch (Exception e) {
        }

        return map;
    }

    public static String convertTeamFromSpinnerTODB(Match m, int index) {
        switch (index) {
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

        ControlPanelFragment.pcIndex = 0;
        ControlPanelFragment.rcIndex = 0;
        EndGameFragment.imageIndex = 0;

        FinishFragment.finishIndex = 0;
        FinishFragment.ticketIndex = 0;
        FinishFragment.crashIndex = 0;
        FinishFragment.defIndex = 0;
        FinishFragment.text = "";
    }

    public static void onSubmit(DatabaseReference dbRef, final FormInfo fi, ArrayList<Cycle> c) {
        int totalCycles = 0, totalScore = 0, totalShots = 0, totalPCmissed = 0, totalPClower = 0, totalPCouter = 0, totalPCinner = 0,
                dbShots = 0, dbPClower = 0, dbPCouter = 0, dbPCinner = 0, dbCPRC = 0, dbCPPC = 0, dbBalanced = 0, dbClimb = 0;

        final DatabaseReference dbRefMatch = dbRef.child(Integer.toString(fi.getMatchNumber())),
                dbRefStats = dbRef.child(Keys.STATS).child(Integer.toString(fi.getMatchNumber()));

        if (fi.getUserComment().toString().trim().equals(""))
            fi.setComment("Empty Comment");
        final Map<String, Object> formInfo = getMap(fi);
        dbRefMatch.setValue(formInfo);
        dbRefMatch.child("CommittedBy").setValue(User.username);

        if (c != null && !c.isEmpty()) {
            totalCycles = c.size();
            for (int i = 0; i < totalCycles; i++) {
                Map<String, Object> cycle = GeneralFunctions.getMap(c.get(i));
                dbRefMatch.child("Cycle " + (i + 1)).setValue(cycle);

                totalPCmissed += c.get(i).pcMissed;
                totalPClower += c.get(i).pcLower;
                totalPCouter += c.get(i).pcOuter;
                totalPCinner += c.get(i).pcInner;
                totalScore += c.get(i).getScore();
                totalShots += c.get(i).getTotalPC();
            }
        }

        dbShots += totalShots;
        dbPClower += totalPClower;
        dbPCouter += totalPCouter;
        dbPCinner += totalPCinner;
        dbCPRC += fi.getCpRotation() == 1 ? 1 : 0;
        dbCPPC += fi.getCpPosition() == 1 ? 1 : 0;
        dbBalanced += fi.getEndGame() == 3 ? 1 : 0;
        dbClimb += fi.getEndGame() == 2 ? 1 : 0;


        dbRefStats.child("Balanced").setValue(Integer.toString(dbBalanced));
        dbRefStats.child("Climb").setValue(Integer.toString(dbClimb));
        dbRefStats.child("PC").setValue(Integer.toString(dbCPPC));
        dbRefStats.child("RC").setValue(Integer.toString(dbCPRC));
        dbRefStats.child("TotalShots").setValue(Integer.toString(dbShots));
        dbRefStats.child("TotalLower").setValue(Integer.toString(dbPClower));
        dbRefStats.child("TotalOuter").setValue(Integer.toString(dbPCouter));
        dbRefStats.child("TotalInner").setValue(Integer.toString(dbPCinner));
        dbRefStats.child("TotalPCScore").setValue(Integer.toString(totalScore));
        dbRefStats.child("TotalCycles").setValue(totalCycles);
    }
}
