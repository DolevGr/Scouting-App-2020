package com.example.primo2020v1.libs;

import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GeneralFunctions {

    public static Map<String,Object> getMap(Object obj)
    {
        Map<String,Object> map = new HashMap<>();
        try{
            Field[] fields = obj.getClass().getFields();

            for(Field f : fields)
            {
                if (!java.lang.reflect.Modifier.isStatic(f.getModifiers()))
                    map.put(f.getName(),f.get(obj));
            }
        }
        catch (Exception e){}
        return map;
    }

    public static void updateTeamSpinner(int i, EditText gameNumber, EditText teamNumber){

        gameNumber.setText(Integer.toString(User.currentGame));

        int x = User.currentGame < User.matches.size()-1 ? User.currentGame : User.matches.size() - 1;

        switch (i) {
            case 0:
                teamNumber.setText(User.matches.get(x).getFirstRedRobot());
                break;
            case 1:
                teamNumber.setText(User.matches.get(x).getSecondRedRobot());
                break;
            case 2:
                teamNumber.setText(User.matches.get(x).getThirdRedRobot());
                break;
            case 3:
                teamNumber.setText(User.matches.get(x).getFirstBlueRobot());
                break;
            case 4:
                teamNumber.setText(User.matches.get(x).getSecondBlueRobot());
                break;
            case 5:
                teamNumber.setText(User.matches.get(x).getThirdBlueRobot());
                break;
            default:
                teamNumber.setText("ERROR game:" + User.currentGame);
                break;
        }
    }
}
