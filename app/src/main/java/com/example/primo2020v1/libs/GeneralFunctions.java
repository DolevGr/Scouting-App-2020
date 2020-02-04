package com.example.primo2020v1.libs;

import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.EndGameFragment;
import com.example.primo2020v1.Fragments.FinishFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;

import java.lang.reflect.Field;
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

        FinishFragment.imageIndex = 0;
        FinishFragment.ticketIndex = 0;
        FinishFragment.crashIndex = 0;
        FinishFragment.text = "";
    }

    public static void setCurrentGameInDB() {
        User.databaseReference.child(Keys.CURRENT_GAME).setValue(Integer.toString(User.currentGame));
    }
}
