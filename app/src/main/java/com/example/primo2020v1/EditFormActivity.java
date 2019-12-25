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

import com.example.primo2020v1.libs.GeneralFunctions;

public class EditFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    Spinner teamSpinnerEdit;
    ArrayAdapter<CharSequence> teamAdapterEdit;
    EditText edGameNumberEdit, edTeamNumberEdit;
    Button btnSearch, btnBackEdit;

    String optionSelected, gameNumber, teamNumber;
    int optionSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnBackEdit = (Button) findViewById(R.id.btnBackEdit);

        edGameNumberEdit = (EditText) findViewById(R.id.edGameNumberEdit);
        edTeamNumberEdit = (EditText) findViewById(R.id.edTeamNumberEdit);

        teamSpinnerEdit = (Spinner) findViewById(R.id.spnTeamEdit);
        teamAdapterEdit = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapterEdit.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamSpinnerEdit.setAdapter(teamAdapterEdit);

        optionSelected = "";
        optionSelectedIndex = 0;
        teamNumber = "";
        gameNumber = "";
        edTeamNumberEdit.setText(teamNumber);
        edGameNumberEdit.setText(gameNumber);

        btnSearch.setOnClickListener(this);
        btnBackEdit.setOnClickListener(this);
        teamSpinnerEdit.setOnItemSelectedListener(this);
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
                optionSelected = "";
                optionSelectedIndex = 0;
                teamNumber = "";
                gameNumber = "";
                GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumberEdit, edTeamNumberEdit);

                finishAndRemoveTask();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        optionSelectedIndex = i;
        optionSelected = (String) teamSpinnerEdit.getSelectedItem();

        Log.d("Spinner Option ", "onClick: " + optionSelected);
        Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();

        GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumberEdit, edTeamNumberEdit);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        optionSelected = (String) teamSpinnerEdit.getItemAtPosition(optionSelectedIndex);
    }
}
