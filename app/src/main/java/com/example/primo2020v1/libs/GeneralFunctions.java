package com.example.primo2020v1.libs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

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

    public static void setCurrentGame() {
        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User.currentGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
                Log.d(TAG, "onDataChange: " + dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d(TAG, "setCurrentGame: Current Game: " + User.currentGame);
    }
}
