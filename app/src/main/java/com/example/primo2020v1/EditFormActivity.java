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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    private Spinner spnTeam;
    private ArrayAdapter<CharSequence> teamAdapter;
    private EditText edGameNumber, edTeamNumber;
    private Button btnSearch, btnBackEdit;
    private ImageView imgFinish, imgTicket, imgCrash, imgEndGame, imgCPnormal, imgCPcolor;
    private EditText edComment;
    private ListView lvCycles;
    private TextView tvTicket, tvCrash;

    private ArrayList<Cycle> cycles;
    private CyclesAdapter cyclesAdapter;
    private String optionSelected, teamNumber;
    private int spnIndex, gameNumber, endGameIndex, finisIndex;
    private int[] endGameImages = {R.drawable.ic_empty, R.drawable.ic_park, R.drawable.ic_climb, R.drawable.ic_balance};
    private final static String TAG = "EditFormActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        cycles = new ArrayList<>();

        imgFinish = findViewById(R.id.imgFinish);
        imgTicket = findViewById(R.id.imgTicket);
        tvTicket = findViewById(R.id.tvTicket);
        imgCrash = findViewById(R.id.imgDidCrash);
        tvCrash = findViewById(R.id.tvCrash);
        imgEndGame = findViewById(R.id.imgEndGame);
        imgCPnormal = findViewById(R.id.imgCPnormal);
        imgCPcolor = findViewById(R.id.imgCPcolor);
        edComment = findViewById(R.id.edComment);
        lvCycles = findViewById(R.id.lvCycles);

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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                if (!edGameNumber.getText().toString().trim().equals("") && !edTeamNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    teamNumber = edTeamNumber.getText().toString().trim();

                    if (isValidGameNumber() && isValidTeam()) {
                        showResults();
                        Log.d(TAG, "onClick: valid");
                    }
                }
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

    private void showResults() {
        DatabaseReference dbRef = User.databaseReference.child("Teams")
                .child(teamNumber).child(Integer.toString(gameNumber));

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numberOfCycles = Integer.parseInt(dataSnapshot.child("TotalCycles").getValue().toString());

                for (int i = 0; i < numberOfCycles; i++) {
                    String stringCycle = "Cycle " + (i+1);
                    Cycle c = dataSnapshot.child(stringCycle).getValue(Cycle.class);
                    cycles.add(c);
                }
                Log.d(TAG, "onDataChange: " + cycles.toString());

                if (cycles != null && !cycles.isEmpty()) {
                    cyclesAdapter = new CyclesAdapter(getApplicationContext(), R.layout.custom_submission_form, cycles);
                    lvCycles.setAdapter(cyclesAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (isValidGameNumber() && spnIndex != i) {
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber-1), i);
            spnIndex = i;
            edGameNumber.setText(Integer.toString(gameNumber));
            edTeamNumber.setText(teamNumber);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private boolean isValidGameNumber() {
        return gameNumber > 0 && gameNumber < User.matches.size();
    }

    private boolean isValidTeam() {
        return User.teams.containsKey(Integer.parseInt(teamNumber));
    }

    private void setImage(ImageView img, int res){
        img.setImageResource(res);
    }
}