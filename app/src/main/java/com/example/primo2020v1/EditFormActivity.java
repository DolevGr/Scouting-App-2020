package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.User;

public class EditFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    Spinner spnTeam;
    ArrayAdapter<CharSequence> teamAdapter;
    EditText edGameNumber, edTeamNumber;
    Button btnSearch, btnBackEdit;

    String optionSelected, teamNumber;
    int spnIndex, gameNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnBackEdit = (Button) findViewById(R.id.btnBackEdit);

        edGameNumber = (EditText) findViewById(R.id.edGameNumberEdit);
        edTeamNumber = (EditText) findViewById(R.id.edTeamNumberEdit);

        spnTeam = (Spinner) findViewById(R.id.spnTeamEdit);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnTeam.setAdapter(teamAdapter);

        optionSelected = "";
        spnIndex = -1;
        teamNumber = "";

        edGameNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edGameNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    spnIndex = spnTeam.getSelectedItemPosition();
                }
            }
        });

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edTeamNumber.getText().toString().trim().equals(""))
                    teamNumber = edTeamNumber.getText().toString().trim();
            }
        });

        btnSearch.setOnClickListener(this);
        btnBackEdit.setOnClickListener(this);
        spnTeam.setOnItemSelectedListener(this);
        spnTeam.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                break;

            case R.id.spnTeamEdit:
                Log.d("Spinner Option ", "onClick: " + optionSelected);
                Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnBackEdit:
                startActivity(new Intent(EditFormActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (isValid() && spnIndex != i) {
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber), i);
            spnIndex = i;
            edGameNumber.setText(Integer.toString(gameNumber));
            edTeamNumber.setText(teamNumber);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private boolean isValid() {
        return gameNumber > 0 && gameNumber < User.matches.size();
    }
}