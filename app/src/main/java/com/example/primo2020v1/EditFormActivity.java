package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.AlertDialogs.CancelFormAlertDialog;
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.FormInfo;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private final static String TAG = "EditFormActivity";

    private Spinner spnTeam;
    private ArrayAdapter<CharSequence> teamAdapter;
    private EditText edGameNumber, edTeamNumber;
    private Button btnSearch, btnBack, btnSubmit;
    private ImageView imgFinish, imgTicket, imgCrash, imgDefence, imgEndGame, imgCPRC, imgCPPC;
    private EditText edComment;
    private ListView lvCycles;

    private DatabaseReference dbRef;
    private ArrayList<Cycle> cycles;
    private FormInfo formInfo;
    private CyclesAdapter cyclesAdapter;
    private String teamNumber;
    private int spnIndex, gameNumber;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        context = this;
        spnIndex = -1;
        teamNumber = "";
        cycles = new ArrayList<>();
        dbRef = User.databaseReference.child(Keys.TEAMS);

        imgFinish = findViewById(R.id.imgFinish);
        imgTicket = findViewById(R.id.imgCard);
        imgCrash = findViewById(R.id.imgDidCrash);
        imgDefence = findViewById(R.id.imgDefence);
        imgEndGame = findViewById(R.id.imgEndGame);
        imgCPRC = findViewById(R.id.imgCPnormal);
        imgCPPC = findViewById(R.id.imgCPcolor);
        edComment = findViewById(R.id.edComment);
        lvCycles = findViewById(R.id.lvCycles);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        edGameNumber = (EditText) findViewById(R.id.edGameNumberEdit);
        edTeamNumber = (EditText) findViewById(R.id.edTeamNumberEdit);
        spnTeam = (Spinner) findViewById(R.id.spnTeamEdit);

        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnTeam.setAdapter(teamAdapter);

        setViewsVisibility(View.INVISIBLE);
        edGameNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edGameNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    spnIndex = spnTeam.getSelectedItemPosition();
                    onSelection(spnIndex);
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
                if (!edTeamNumber.getText().toString().trim().equals("") && isValidTeamNumber()) {
                    teamNumber = edTeamNumber.getText().toString().trim();
                }
            }
        });

        btnSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        spnTeam.setOnItemSelectedListener(this);
        imgCPPC.setOnClickListener(this);
        imgCPRC.setOnClickListener(this);
        imgFinish.setOnClickListener(this);
        imgEndGame.setOnClickListener(this);
        imgTicket.setOnClickListener(this);
        imgCrash.setOnClickListener(this);
        imgDefence.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                if (!edGameNumber.getText().toString().trim().equals("") &&
                        !edTeamNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    teamNumber = edTeamNumber.getText().toString().trim();

                    if (isValidGameNumber() && isValidTeamNumber()) {
                        showResults();

//                        if (User.matches.get(gameNumber-1).hasTeam(teamNumber)) {
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Team does not play in this match", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid team or Match", Toast.LENGTH_SHORT).show();
                    }
                    btnSubmit.setOnClickListener(this);
                }
                break;

            case R.id.btnSubmit:
                formInfo.setComment(edComment.getText().toString().trim());
                GeneralFunctions.onSubmit(dbRef, formInfo, cycles);
                finish();
                startActivity(new Intent(EditFormActivity.this, DrawerActivity.class));
                break;

            case R.id.btnBack:
                openDialog();
                break;

            case R.id.imgCPnormal:
                formInfo.setCpRotation((formInfo.getCpRotation() + 1) % User.controlPanelRotation.length);
                imgCPRC.setImageResource(User.controlPanelRotation[formInfo.getCpRotation()]);
                break;

            case R.id.imgCPcolor:
                formInfo.setCpPosition((formInfo.getCpPosition() + 1) % User.controlPanelPosition.length);
                imgCPPC.setImageResource(User.controlPanelPosition[formInfo.getCpPosition()]);
                break;

            case R.id.imgEndGame:
                formInfo.setEndGame((formInfo.getEndGame() + 1) % User.endGameImages.length);
                imgEndGame.setImageResource(User.endGameImages[formInfo.getEndGame()]);
                break;

            case R.id.imgFinish:
                formInfo.setFinish((formInfo.getFinish() + 1) % User.finishImages.length);
                imgFinish.setImageResource(User.finishImages[formInfo.getFinish()]);
                break;

            case R.id.imgDidCrash:
                formInfo.setCrash((formInfo.getCrash() + 1) % User.finishCrash.length);
                imgCrash.setImageResource(User.finishCrash[formInfo.getCrash()]);
                break;

            case R.id.imgCard:
                formInfo.setTicket((formInfo.getTicket() + 1) % User.finishTickets.length);
                imgTicket.setImageResource(User.finishTickets[formInfo.getTicket()]);
                break;

            case R.id.imgDefence:
                formInfo.setDefence((formInfo.getDefence() + 1) % User.finishDefence.length);
                imgDefence.setImageResource(User.finishDefence[formInfo.getDefence()]);
                break;

            default:
                break;
        }
    }

    private void openDialog() {
        CancelFormAlertDialog dialog = new CancelFormAlertDialog();
        dialog.show(getSupportFragmentManager(), "cancel form dialog");
    }

    private void showResults() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(teamNumber)) {
                    dataSnapshot = dataSnapshot.child(teamNumber);

                    if (cyclesAdapter != null && !cyclesAdapter.isEmpty())
                        cyclesAdapter.clear();

                    getFormInfo(dataSnapshot.child(Integer.toString(gameNumber)));
                    getCycles(dataSnapshot);

                    if (cycles != null && !cycles.isEmpty()) {
                        cyclesAdapter = new CyclesAdapter(context, R.layout.custom_cycles_adapter, cycles);
                        lvCycles.setAdapter(cyclesAdapter);
                    }
                    placeInFormInfo();
                } else {
                    Toast.makeText(EditFormActivity.this, "Team was not found or hasn't played yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spnIndex != i) {
            onSelection(i);
        }

        if (isValidGameNumber())
            edGameNumber.setText(Integer.toString(gameNumber));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void onSelection(int i) {
        if (isValidGameNumber() ) {
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber - 1), i);
            spnIndex = i;
        }

        edTeamNumber.setText(teamNumber);
    }

    private boolean isValidGameNumber() {
        return gameNumber > 0 && gameNumber < User.matches.size();
    }

    private boolean isValidTeamNumber() {
        return User.participants.containsKey(Integer.parseInt(teamNumber));
    }

    private void getCycles(DataSnapshot dataSnapshot) {
        int numberOfCycles = Integer.parseInt(dataSnapshot.child(Keys.STATS)
                .child(Integer.toString(gameNumber)).child("totalCycles").getValue().toString());
        DataSnapshot dsCycles = dataSnapshot.child(Integer.toString(gameNumber));

        for (int i = 0; i < numberOfCycles; i++) {
            String stringCycle = "Cycle " + (i + 1);
            Cycle c = dsCycles.child(stringCycle).getValue(Cycle.class);
            cycles.add(c);
        }
    }

    private void getFormInfo(DataSnapshot dataSnapshot) {
        formInfo = new FormInfo();
        formInfo.setCpRotation(Integer.parseInt(dataSnapshot.child("cpRotation").getValue().toString()));
        formInfo.setCpPosition(Integer.parseInt(dataSnapshot.child("cpPosition").getValue().toString()));
        formInfo.setEndGame(Integer.parseInt(dataSnapshot.child("endGame").getValue().toString()));
        formInfo.setFinish(Integer.parseInt(dataSnapshot.child("finish").getValue().toString()));
        formInfo.setCrash(Integer.parseInt(dataSnapshot.child("crash").getValue().toString()));
        formInfo.setTicket(Integer.parseInt(dataSnapshot.child("ticket").getValue().toString()));
        formInfo.setDefence(Integer.parseInt(dataSnapshot.child("defence").getValue().toString()));
        formInfo.setComment(dataSnapshot.child("comment").getValue().toString());
        formInfo.setGameNumber(gameNumber);
    }

    private void placeInFormInfo() {
        setViewsVisibility(View.VISIBLE);

        imgCPRC.setImageResource(User.controlPanelRotation[formInfo.getCpRotation()]);
        imgCPPC.setImageResource(User.controlPanelPosition[formInfo.getCpPosition()]);
        imgEndGame.setImageResource(User.endGameImages[formInfo.getEndGame()]);
        imgFinish.setImageResource(User.finishImages[formInfo.getFinish()]);
        imgTicket.setImageResource(User.finishTickets[formInfo.getTicket()]);
        imgCrash.setImageResource(User.finishCrash[formInfo.getCrash()]);
        imgDefence.setImageResource(User.finishDefence[formInfo.getDefence()]);

        if (!formInfo.getUserComment().equals("Empty Comment"))
            edComment.setText(formInfo.getUserComment());
        else
            edComment.setHint(formInfo.getUserComment());
    }

    private void setViewsVisibility(int visibility) {
        imgFinish.setVisibility(visibility);
        imgTicket.setVisibility(visibility);
        imgCrash.setVisibility(visibility);
        imgDefence.setVisibility(visibility);
        imgEndGame.setVisibility(visibility);
        imgCPRC.setVisibility(visibility);
        imgCPPC.setVisibility(visibility);
        edComment.setVisibility(visibility);
    }
}