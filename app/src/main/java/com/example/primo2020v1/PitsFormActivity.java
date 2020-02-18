package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.AlertDialogs.CancelFormAlertDialog;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DatabaseReference;

public class PitsFormActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edRobotHeight, edRobotChassie, edRobotMass, edTeamNumber, edComment;
    private TextView tvTeamName;
    private ImageView imgCPPC, imgCPRC, imgEndGame;
    private Switch switchAuto;
    private Button btnSubmit, btnBack;
    private int cpIndex, rcIndex, endgameIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pits_form);

        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvTeamName = findViewById(R.id.tvTeamName);
        edTeamNumber = findViewById(R.id.edTeamNumber);
        edRobotHeight = findViewById(R.id.edRobotHeight);
        edRobotChassie = findViewById(R.id.edRobotChassie);
        edRobotMass = findViewById(R.id.edRobotMass);
        edComment = findViewById(R.id.edComment);
        imgCPPC = findViewById(R.id.imgCPPC);
        imgCPRC = findViewById(R.id.imgCPRC);
        imgEndGame = findViewById(R.id.imgEndGame);
        switchAuto = findViewById(R.id.switchAuto);

        cpIndex = 0;
        rcIndex = 0;
        endgameIndex = 1;

        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgCPPC.setOnClickListener(this);
        imgCPRC.setOnClickListener(this);
        imgEndGame.setOnClickListener(this);

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
                    Toast.makeText(this, "Field is missing", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnBack:
                openDialog();
                break;
        }
    }

    private boolean isValid() {
        String name = edTeamNumber.getText().toString().trim();
        return !edRobotChassie.getText().toString().trim().equals("") &&
                !edTeamNumber.getText().toString().trim().equals("") &&
                !edRobotHeight.getText().toString().trim().equals("") &&
                !edRobotMass.getText().toString().trim().equals("") &&
                !edComment.getText().toString().trim().equals("") &&
                User.participants.containsKey(Integer.parseInt(name));
    }

    private void onSubmit() {
        DatabaseReference dbRef = User.databaseReference.child(Keys.TEAMS).child(edTeamNumber.getText().toString().trim()).child(Keys.PIT);

        dbRef.child("RobotHeight").setValue(edRobotHeight.getText().toString().trim());
        dbRef.child("RobotChassie").setValue(edRobotChassie.getText().toString().trim());
        dbRef.child("RobotMass").setValue(edRobotMass.getText().toString().trim());
        dbRef.child("Comment").setValue(edComment.getText().toString().trim());
        dbRef.child("HasAuto").setValue(switchAuto.isActivated());
        dbRef.child("CPRC").setValue(rcIndex);
        dbRef.child("CPPC").setValue(cpIndex);
        dbRef.child("EndGame").setValue(endgameIndex);
    }

    private void openDialog() {
        CancelFormAlertDialog dialog = new CancelFormAlertDialog();
        dialog.show(getSupportFragmentManager(), "cancel form dialog");
    }
}
