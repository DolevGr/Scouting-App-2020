package com.example.primo2020v1.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.primo2020v1.GameFormFragments.ControlPanelFragment;
import com.example.primo2020v1.GameFormFragments.EndGameFragment;
import com.example.primo2020v1.GameFormFragments.FinishFragment;
import com.example.primo2020v1.GameFormFragments.MatchSettingsFragment;
import com.example.primo2020v1.GameFormFragments.PowerCellsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
        MatchSettingsFragment.isFromUser = true;
        MatchSettingsFragment.teamNumber = "";
//        MatchSettingsFragment.spnIndex = -1;
        MatchSettingsFragment.gameNumber = User.formMatch;

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

    static int totalCycles, totalScore, totalShots, totalPCmissed, totalPClower, totalPCouter, totalPCinner,
            dbCPRC, dbCPPC, dbBalanced, dbClimb, dbTimesCrashed, dbTimesDefended, dbYellowCard, dbRedCard;

    public static void onSubmit(DatabaseReference dbRef, final FormInfo fi, ArrayList<Cycle> c) {
        Log.d(TAG, "onSubmit: " + fi.getMatchNumber());
        final DatabaseReference dbRefMatch = dbRef.child(Integer.toString(fi.getMatchNumber())),
                dbRefStats = dbRef.child(Keys.STATS),
                dbRefComments = User.databaseReference.child(Keys.COMMENTS)
                        .child(fi.getTeamNumber()).child(Integer.toString(fi.getMatchNumber()));
        resetSubmission();

        if (fi.getUserComment().toString().trim().equals(""))
            fi.setComment("Empty Comment");
        final Map<String, Object> formInfo = getMap(fi);
        dbRefMatch.setValue(formInfo);
        dbRefMatch.child("CommittedBy").setValue(User.username);

        String newComment = fi.getUserComment().toString();
        dbRefComments.setValue(newComment);

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
        dbCPRC += fi.getCpRotation() == 1 ? 1 : 0;
        dbCPPC += fi.getCpPosition() == 1 ? 1 : 0;
        dbBalanced += fi.getEndGame() == 3 ? 1 : 0;
        dbClimb += fi.getEndGame() == 2 ? 1 : 0;
        dbYellowCard = fi.getTicket() == 1 ? 1 : 0;
        dbRedCard = fi.getTicket() == 2 ? 1 : 0;
        dbTimesDefended = fi.getDefence();

        Statistics thisMatch = new Statistics(1, totalCycles, totalScore, totalShots,
                totalPCmissed, totalPClower, totalPCouter, totalPCinner,
                dbCPRC, dbCPPC, dbBalanced, dbClimb,
                dbTimesCrashed, dbTimesDefended, dbYellowCard, dbRedCard);
        dbRefStats.child(Integer.toString(fi.getMatchNumber())).setValue(thisMatch);

        dbRefStats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Statistics statsSum;
                if (dataSnapshot.hasChild(Keys.STATS_SUM)) {
                    dataSnapshot = dataSnapshot.child(Keys.STATS_SUM);
                    statsSum = dataSnapshot.getValue(Statistics.class);
                    thisMatch.addAll(statsSum);
                }
                dbRefStats.child(Keys.STATS_SUM).setValue(thisMatch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static void resetSubmission() {
        totalCycles = 0;
        totalScore = 0;
        totalShots = 0;
        totalPCmissed = 0;
        totalPClower = 0;
        totalPCouter = 0;
        totalPCinner = 0;
        dbCPRC = 0;
        dbCPPC = 0;
        dbBalanced = 0;
        dbClimb = 0;
        dbTimesCrashed = 0;
        dbTimesDefended = 0;
        dbYellowCard = 0;
        dbRedCard = 0;
    }


    public static void writeObjectToFile(String filename, Context context, Serializable ser) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStream.writeObject(ser);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String filename, Context context) {
        try {
            ObjectInputStream objectReader = new ObjectInputStream(context.openFileInput(filename));
            Object obj = objectReader.readObject();
            objectReader.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <K, V> void writeToFile(String filename, Context context, Map<K, V> map) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            for (Map.Entry<K, V> entry : map.entrySet()) {
                outputStreamWriter.write(entry.getKey().toString() + "=" + entry.getValue().toString() + System.lineSeparator());
            }
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> readFromFile(String filename, Context context) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
            if (bufferedReader.ready()) {
                Map<String, String> map = new HashMap<>();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] split = line.split("=", 2);
                    map.put(split[0], split[1]);
                }

                bufferedReader.close();
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
