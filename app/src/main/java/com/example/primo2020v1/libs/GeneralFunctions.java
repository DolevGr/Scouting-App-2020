package com.example.primo2020v1.libs;

import android.widget.EditText;

public class GeneralFunctions {

    public static void updateTeamSpinner(int i, EditText gameNumber, EditText teamNumber, boolean changeGameNumber){

        if(changeGameNumber)
            gameNumber.setText(Integer.toString(User.currentGame));

        int x = User.currentGame < User.NUMBER_OF_MATCHES-1 ? User.currentGame : User.NUMBER_OF_MATCHES - 1;

        switch (i) {
            case 0:
                teamNumber.setText(User.matches.get(x).getRedTeam().getFirstRobot());
                break;
            case 1:
                teamNumber.setText(User.matches.get(x).getRedTeam().getSecondRobot());
                break;
            case 2:
                teamNumber.setText(User.matches.get(x).getRedTeam().getThirdRobot());
                break;
            case 3:
                teamNumber.setText(User.matches.get(x).getBlueTeam().getFirstRobot());
                break;
            case 4:
                teamNumber.setText(User.matches.get(x).getBlueTeam().getSecondRobot());
                break;
            case 5:
                teamNumber.setText(User.matches.get(x).getBlueTeam().getThirdRobot());
                break;
            default:
                teamNumber.setText("ERROR game:" + User.currentGame);
                break;
        }
    }
}
