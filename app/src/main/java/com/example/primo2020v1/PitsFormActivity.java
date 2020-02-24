package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.AlertDialogs.CancelFormAlertDialog;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.Pit;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DatabaseReference;

public class PitsFormActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edRobotMass, edComment, edWheelsOther;
    private AutoCompleteTextView edTeamNumber;
    private TextView tvTeamName;
    private ImageView imgCPPC, imgCPRC, imgEndGame;
    private Switch switchAuto, switchTrench, switchBumpers;
    private Button btnSubmit, btnBack;
    private Spinner spnLanguage, spnWheels, spnIntake, spnPCCarry, spnShoot;
    private int cpIndex, rcIndex, endgameIndex;
    private String selectedWheels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pits_form);

        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvTeamName = findViewById(R.id.tvTeamName);
        edTeamNumber = findViewById(R.id.edTeamNumber);
        edRobotMass = findViewById(R.id.edRobotMass);
        edComment = findViewById(R.id.edComment);

        spnLanguage = findViewById(R.id.spnLanguage);
        spnWheels = findViewById(R.id.spnWheels);
        edWheelsOther = findViewById(R.id.edWheelsOther);
        spnIntake = findViewById(R.id.spnIntake);
        spnPCCarry = findViewById(R.id.spnPCCarry);
        spnShoot = findViewById(R.id.spnShoot);

        imgCPPC = findViewById(R.id.imgCPPC);
        imgCPRC = findViewById(R.id.imgCPRC);
        imgEndGame = findViewById(R.id.imgEndGame);

        switchAuto = findViewById(R.id.switchAuto);
        switchTrench = findViewById(R.id.switchTrench);
        switchBumpers = findViewById(R.id.switchBumpers);

        selectedWheels = "";
        cpIndex = 0;
        rcIndex = 0;
        endgameIndex = 1;

        ArrayAdapter languageAdapter = ArrayAdapter.createFromResource(this, R.array.languages, R.layout.support_simple_spinner_dropdown_item);
        languageAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnLanguage.setAdapter(languageAdapter);

        ArrayAdapter wheelsAdapter = ArrayAdapter.createFromResource(this, R.array.wheels, R.layout.support_simple_spinner_dropdown_item);
        wheelsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnWheels.setAdapter(wheelsAdapter);

        ArrayAdapter intakeAdapter = ArrayAdapter.createFromResource(this, R.array.intake, R.layout.support_simple_spinner_dropdown_item);
        intakeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnIntake.setAdapter(intakeAdapter);

        ArrayAdapter carryAdapter = ArrayAdapter.createFromResource(this, R.array.PCCarry, R.layout.support_simple_spinner_dropdown_item);
        carryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnPCCarry.setAdapter(carryAdapter);

        ArrayAdapter shootAdapter = ArrayAdapter.createFromResource(this, R.array.shoot, R.layout.support_simple_spinner_dropdown_item);
        shootAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnShoot.setAdapter(shootAdapter);

        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgCPPC.setOnClickListener(this);
        imgCPRC.setOnClickListener(this);
        imgEndGame.setOnClickListener(this);

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_dropdown_item_1line, User.participants.keySet().toArray());
        edTeamNumber.setAdapter(adapter);
        edTeamNumber.setThreshold(1);

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTeamName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = edTeamNumber.getText().toString().trim();
                if (!number.equals("")) {
                    if (User.participants.containsKey(Integer.parseInt(number))) {
                        tvTeamName.setText(User.participants.get(Integer.parseInt(number)).replaceAll(" ", "\n"));
                    }
                }
            }
        });

        spnWheels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWheels = (String) spnWheels.getSelectedItem();

                if (position == 2) {
                    edWheelsOther.setVisibility(View.VISIBLE);
                }
                else {
                    edWheelsOther.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWheels = (String) spnWheels.getSelectedItem();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCPRC:
                imgCPRC.setImageResource(User.controlPanelRotation[++rcIndex % User.controlPanelRotation.length]);
                break;

            case R.id.imgCPPC:
                imgCPPC.setImageResource(User.controlPanelPosition[++cpIndex % User.controlPanelPosition.length]);
                break;

            case R.id.imgEndGame:
                endgameIndex++;
                endgameIndex %= User.endGameImages.length;
                if (endgameIndex == 0)
                    endgameIndex++;
                imgEndGame.setImageResource(User.endGameImages[endgameIndex]);
                break;

            case R.id.btnSubmit:
                if (isValid()) {
                    onSubmit();
                    finish();
                    startActivity(new Intent(PitsFormActivity.this, DrawerActivity.class));
                } else {
                    Toast.makeText(this, "Fields are missing", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnBack:
                openDialog();
                break;
        }
    }

    private boolean isValid() {
        String name = edTeamNumber.getText().toString().trim();
        boolean valid = !edTeamNumber.getText().toString().trim().equals("") &&
                !edRobotMass.getText().toString().trim().equals("");
//        && User.participants.containsKey(Integer.parseInt(name));

        if (selectedWheels.equals("Other"))
            valid = valid && !edWheelsOther.getText().toString().trim().equals("");

        return valid;
    }

    private void onSubmit() {
        DatabaseReference dbRef = User.databaseReference.child(Keys.TEAMS).child(edTeamNumber.getText().toString().trim()).child(Keys.PIT);
        String wheels = edWheelsOther.getText().toString().trim();
        if (!selectedWheels.equals("Other"))
            wheels = (String) spnWheels.getSelectedItem();

        Pit pit = new Pit(edRobotMass.getText().toString().trim(), edComment.getText().toString().trim(), spnLanguage.getSelectedItem().toString(),
                wheels, spnIntake.getSelectedItem().toString(), spnPCCarry.getSelectedItem().toString(), spnShoot.getSelectedItem().toString(),
                switchAuto.isChecked(), switchTrench.isChecked(), switchBumpers.isChecked(),
                endgameIndex, rcIndex, cpIndex);

        dbRef.setValue(pit);
    }

    private void openDialog() {
        CancelFormAlertDialog dialog = new CancelFormAlertDialog();
        dialog.show(getSupportFragmentManager(), "cancel form dialog");
    }
}
