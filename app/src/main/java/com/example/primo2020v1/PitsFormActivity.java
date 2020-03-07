package com.example.primo2020v1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.AlertDialogs.CancelFormAlertDialog;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.Pit;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PitsFormActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PitsFormActivity";
    private EditText edRobotMass, edComment, edWheelsOther;
    private AutoCompleteTextView edTeamNumber;
    private TextView tvTeamName;
    private ImageView imgCPPC, imgCPRC, imgEndGame;
    private Switch switchAuto, switchTrench, switchBumpers;
    private Button btnSubmit, btnBack, btnSearch;
    private Spinner spnLanguage, spnWheels, spnIntake, spnPCCarry, spnShoot;

    private DatabaseReference dbRefPit = User.databaseReference.child(Keys.TEAMS);
    private int cpIndex = 0, rcIndex = 0, endgameIndex = 1, teamNumber = 0;
    private String selectedWheels = "";
    private boolean isUnlocked = false;
    private Pit pitDB, thisPit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pits_form);

        btnSearch = findViewById(R.id.btnSearchPit);
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmitStrats);
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
        changeState();


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
                tvTeamName.setTextColor(Color.DKGRAY);
                isUnlocked = false;
                changeState();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = edTeamNumber.getText().toString().trim();
                if (!number.equals("")) {
                    teamNumber = Integer.parseInt(number);
                    if (User.participants.containsKey(teamNumber))
                        tvTeamName.setText(User.participants.get(teamNumber).replaceAll(" ", "\n"));
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

        btnSearch.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgCPPC.setOnClickListener(this);
        imgCPRC.setOnClickListener(this);
        imgEndGame.setOnClickListener(this);
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

            case R.id.btnSubmitStrats:
                if (isValid()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Change pit Info")
                            .setMessage("There is an already existing pit form. Do you want to override it?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                onSubmit();
                                finish();
                                startActivity(new Intent(PitsFormActivity.this, DrawerActivity.class));
                            })
                            .setNegativeButton("No", (dialog, which) -> { })
                            .show();

                } else {
                    Toast.makeText(this, "Fields are missing", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnBack:
                openDialog();
                break;

            case R.id.btnSearchPit:
                if (User.participants.containsKey(teamNumber)) {
                    dbRefPit.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(Integer.toString(teamNumber))) {
                                DataSnapshot dsTeam = dataSnapshot.child(Integer.toString(teamNumber));
                                if (dsTeam.hasChild(Keys.PIT)) {
                                    DataSnapshot dsPit = dsTeam.child(Keys.PIT);
                                    pitDB = dsPit.getValue(Pit.class);
                                    setPitInfo();
                                    tvTeamName.setTextColor(Color.RED);
                                } else {
                                    resetPitForm();
                                    tvTeamName.setTextColor(Color.GREEN);
                                }
                            } else {
                                resetPitForm();
                                tvTeamName.setTextColor(Color.GREEN);
                            }

                            isUnlocked = true;
                            changeState();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    resetPitForm();
                    isUnlocked = true;
                    changeState();
                }

        }
    }

    private boolean isValid() {
        boolean valid = !edTeamNumber.getText().toString().trim().equals("") &&
                !edRobotMass.getText().toString().trim().equals("");

        return selectedWheels.equals("Other") ? valid && !edWheelsOther.getText().toString().trim().equals("") : valid ;
    }

    private void onSubmit() {
        DatabaseReference dbRef = User.databaseReference.child(Keys.TEAMS).child(edTeamNumber.getText().toString().trim()).child(Keys.PIT);
        String wheels = edWheelsOther.getText().toString().trim();
        if (!selectedWheels.equals("Other"))
            wheels = (String) spnWheels.getSelectedItem();

        thisPit = new Pit(edRobotMass.getText().toString().trim(), edComment.getText().toString().trim(), spnLanguage.getSelectedItem().toString(),
                wheels, spnIntake.getSelectedItem().toString(), spnPCCarry.getSelectedItem().toString(), spnShoot.getSelectedItem().toString(),
                switchAuto.isChecked(), switchTrench.isChecked(), switchBumpers.isChecked(),
                endgameIndex, rcIndex, cpIndex);

        if (pitDB != null && !thisPit.equals(pitDB))
            dbRef.setValue(thisPit);
    }

    private void openDialog() {
        CancelFormAlertDialog dialog = new CancelFormAlertDialog();
        dialog.show(getSupportFragmentManager(), "cancel form dialog");
    }

    private void setPitInfo() {
        cpIndex = pitDB.getCppc();
        rcIndex = pitDB.getCprc();
        endgameIndex = pitDB.getEndGame();
        selectedWheels = pitDB.getWheels();

        edRobotMass.setText(pitDB.getMass());
        if (!selectedWheels.equals("Empty Comment")) {
            edComment.setText(pitDB.getComment());
        }

        spnWheels.setSelection(pitDB.getWheelsIndex());
        if (pitDB.getWheelsIndex() == 2) {
            edWheelsOther.setText(pitDB.getWheels());
        }
        spnLanguage.setSelection(pitDB.getLanguageIndex());
        spnIntake.setSelection(pitDB.getIntakeIndex());
        spnPCCarry.setSelection(pitDB.getCarryIndex());
        spnShoot.setSelection(pitDB.getShootIndex());

        imgCPRC.setImageResource(User.controlPanelRotation[rcIndex]);
        imgCPPC.setImageResource(User.controlPanelPosition[cpIndex]);
        imgEndGame.setImageResource(User.endGameImages[endgameIndex]);

        switchAuto.setChecked(pitDB.isHasAuto());
        switchTrench.setChecked(pitDB.isCanTrench());
        switchBumpers.setChecked(pitDB.isCanBumpers());

    }

    private void changeState() {
        edRobotMass.setClickable(isUnlocked);
        edRobotMass.setEnabled(isUnlocked);

        edComment.setClickable(isUnlocked);
        edComment.setEnabled(isUnlocked);

        if (spnWheels.getSelectedItemPosition() == 2)
            edWheelsOther.setVisibility(isUnlocked ? View.VISIBLE : View.INVISIBLE);
        edWheelsOther.setClickable(isUnlocked);
        edWheelsOther.setEnabled(isUnlocked);

        spnLanguage.setEnabled(isUnlocked);
        spnLanguage.setClickable(isUnlocked);

        spnShoot.setEnabled(isUnlocked);
        spnShoot.setClickable(isUnlocked);

        spnPCCarry.setEnabled(isUnlocked);
        spnPCCarry.setClickable(isUnlocked);

        spnIntake.setEnabled(isUnlocked);
        spnIntake.setClickable(isUnlocked);

        spnWheels.setEnabled(isUnlocked);
        spnWheels.setClickable(isUnlocked);

        imgCPPC.setClickable(isUnlocked);
        imgCPPC.setEnabled(isUnlocked);

        imgCPRC.setClickable(isUnlocked);
        imgCPRC.setEnabled(isUnlocked);

        imgEndGame.setClickable(isUnlocked);
        imgEndGame.setEnabled(isUnlocked);

        switchAuto.setClickable(isUnlocked);
        switchAuto.setEnabled(isUnlocked);

        switchTrench.setClickable(isUnlocked);
        switchTrench.setEnabled(isUnlocked);

        switchBumpers.setClickable(isUnlocked);
        switchBumpers.setEnabled(isUnlocked);
    }

    private void resetPitForm() {
        Log.d(TAG, "resetPitForm: ");
        cpIndex = 0;
        rcIndex = 0;
        endgameIndex = 1;
        selectedWheels = "";

        edRobotMass.setText("");
        edWheelsOther.setText("");
        edWheelsOther.setVisibility(View.INVISIBLE);
        edComment.setText("");

        spnWheels.setSelection(0);
        spnLanguage.setSelection(0);
        spnIntake.setSelection(0);
        spnPCCarry.setSelection(0);
        spnShoot.setSelection(0);

        imgEndGame.setImageResource(User.endGameImages[endgameIndex]);
        imgCPRC.setImageResource(User.controlPanelRotation[rcIndex]);
        imgCPPC.setImageResource(User.controlPanelPosition[cpIndex]);

        switchAuto.setChecked(false);
        switchTrench.setChecked(false);
        switchBumpers.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        openDialog();
    }
}
