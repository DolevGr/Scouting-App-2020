package com.example.primo2020v1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.User;

public class GameFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner teamSpinner;
    ArrayAdapter<CharSequence> teamAdapter;
    Button btnNext, btnTest;
    EditText edGameNumber, edTeamNumber;

    String optionSelected, gameNumber, teamNumber;
    int optionSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        optionSelected = "";

        edGameNumber = (EditText) findViewById(R.id.edGameNumber);
        edTeamNumber = (EditText) findViewById(R.id.edTeamNumber);

        btnNext = (Button) findViewById(R.id.btnNext);

        teamSpinner = (Spinner) findViewById(R.id.spnTeam);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);

        optionSelectedIndex = 0;
        teamNumber = "";
        gameNumber = "";
        edTeamNumber.setText(teamNumber);
        edGameNumber.setText(gameNumber);

        btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);

        btnNext.setOnClickListener(this);
        teamSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                Log.d("Next Button ", "onClick: Next Stage in GameForm");
                Toast.makeText(this, "BtnNext ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.spnTeam:
                Log.d("Spinner Option ", "onClick: " + optionSelected);
                Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnTest:
                User.currentGame++;
                Toast.makeText(this,"Current Game: " + User.currentGame, Toast.LENGTH_SHORT).show();
                updateTeamSpinner(optionSelectedIndex);
                break;
            default:
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        optionSelectedIndex = i;
        optionSelected = (String) teamSpinner.getSelectedItem();
        Log.d("Spinner Option ", "onClick: " + optionSelected);
        Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();

        updateTeamSpinner(optionSelectedIndex);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        optionSelected = (String) teamSpinner.getItemAtPosition(optionSelectedIndex);
    }

    public void updateTeamSpinner(int i){
        edGameNumber.setText(Integer.toString(User.currentGame));

        if(User.currentGame < User.NUMBER_OF_MATCHES-1){
            switch (i) {
                case 0:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getRedTeam().getFirstRobot());
                    break;
                case 1:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getRedTeam().getSecondRobot());
                    break;
                case 2:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getRedTeam().getThirdRobot());
                    break;
                case 3:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getBlueTeam().getFirstRobot());
                    break;
                case 4:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getBlueTeam().getSecondRobot());
                    break;
                case 5:
                    edTeamNumber.setText(User.matches.get(User.currentGame).getBlueTeam().getThirdRobot());
                    break;
                default:
                    edTeamNumber.setText("ERROR game:" + User.currentGame);
                    break;
            }
        }
    }
}
