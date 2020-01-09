package com.example.primo2020v1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.User;

public class GameFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner teamSpinner;
    ArrayAdapter<CharSequence> teamAdapter;
    Button btnNext, btnTest, btnBack;
    EditText edGameNumber, edTeamNumber;
    ConstraintLayout[] clEditForm;
    ConstraintLayout clGlobal;
    ViewGroup clGlobalParent;
    LayoutInflater layoutInflater;

    String optionSelected, gameNumber, teamNumber;
    int optionSelectedIndex, btnNextCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        clGlobal = findViewById(R.id.clGlobal);
        clGlobalParent = (ViewGroup) clGlobal.getParent();
        clEditForm = new ConstraintLayout[2];
        clEditForm[0] = findViewById(R.id.clBeforeMatch);
        clEditForm[1] = findViewById(R.id.clMiddleMatch);


        edGameNumber = (EditText) findViewById(R.id.edGameNumber);
        edTeamNumber = (EditText) findViewById(R.id.edTeamNumber);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack = (Button) findViewById(R.id.btnBack);

        teamSpinner = (Spinner) findViewById(R.id.spnTeam);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);

        optionSelected = "";
        optionSelectedIndex = 0;
        teamNumber = "";
        gameNumber = "";
        edTeamNumber.setText(teamNumber);
        //edGameNumber.setText(gameNumber);

        btnTest = (Button) findViewById(R.id.btnTest);
        //btnTest.setOnClickListener(this);

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        teamSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean phase = false;

        switch (view.getId()){
            case R.id.btnNext:
                phase = true;
                Log.d("Next Button ", "onClick: Next Stage in GameForm");
                Toast.makeText(this, "BtnNext ", Toast.LENGTH_SHORT).show();
                btnNextCounter++;
                break;

            case R.id.spnTeam:
                Log.d("Spinner Option ", "onClick: " + optionSelected);
                Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnTest:
                User.currentGame++;
                Toast.makeText(this,"Current Game: " + User.currentGame, Toast.LENGTH_SHORT).show();
                GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);
                break;

            case R.id.btnBack:
                phase = true;
                btnNextCounter--;
//                optionSelected = "";
//                optionSelectedIndex = 0;
//                teamNumber = "";
//                gameNumber = "";
                GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);

                finish();
                break;

            default:
                break;

        }

//        if(phase){
//            if(btnNextCounter == clEditForm.length)
//                finish();
//            else{
//                clGlobalParent.removeAllViews();
//                clGlobalParent.addView(clEditForm[btnNextCounter]);
//            }
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        optionSelectedIndex = i;
        optionSelected = (String) teamSpinner.getSelectedItem();

        Log.d("Spinner Option ", "onClick: " + optionSelected);
        Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();

        GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        optionSelected = (String) teamSpinner.getItemAtPosition(optionSelectedIndex);
    }
}
