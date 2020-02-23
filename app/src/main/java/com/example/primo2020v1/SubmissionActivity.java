package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.AlertDialogs.MissingTeamNumberAlertDialog;
import com.example.primo2020v1.AlertDialogs.OverrideExistingMatchAlertDialog;
import com.example.primo2020v1.utils.Cycle;
import com.example.primo2020v1.utils.FormInfo;
import com.example.primo2020v1.utils.GeneralFunctions;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SubmissionActivity";
    private ListView lvCycles;
    private ImageView imgEndGame, imgFinish;
    private Button btnSubmit, btnBack;
    private ImageView imgPCRC, imgCPPC, imgTicket, imgCrash, imgDefence;
    private TextView tvComment;
    private String teamNumber;
    private int gameNumber;
    private CyclesAdapter adapter;
    public static boolean isValid = true;

    private Intent intent, finishedForm, i;
    public FormInfo fi;
    public ArrayList<Cycle> c;

    public DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        dbRef = User.databaseReference.child("Teams");

        lvCycles = findViewById(R.id.lvCycles);
        imgTicket = findViewById(R.id.imgTicket);
        imgCrash = findViewById(R.id.imgDidCrash);
        imgDefence = findViewById(R.id.imgDefence);
        imgFinish = findViewById(R.id.imgFinish);
        imgEndGame = findViewById(R.id.imgEndGame);
        imgPCRC = findViewById(R.id.imgCPnormal);
        imgCPPC = findViewById(R.id.imgCPcolor);

        tvComment = findViewById(R.id.tvComment);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        intent = getIntent();
        if (intent.hasExtra(Keys.FORM_INFO) && intent.hasExtra(Keys.FINISH_PC)) {
            fi = intent.getParcelableExtra(Keys.FORM_INFO);
            c = intent.getParcelableArrayListExtra(Keys.FINISH_PC);

            if (c != null && !c.isEmpty()) {
                adapter = new CyclesAdapter(this, R.layout.custom_cycles_adapter, c);
                lvCycles.setAdapter(adapter);
            }

            setControlPanels();
            imgEndGame.setImageResource(User.endGameImages[fi.getEndGame()]);
            imgFinish.setImageResource(User.finishImages[fi.getFinish()]);
            imgTicket.setImageResource(User.finishTickets[fi.getTicket()]);
            imgCrash.setImageResource(User.finishCrash[fi.getCrash()]);
            imgDefence.setImageResource(User.finishDefence[fi.getDefence()]);
            tvComment.setText(fi.getUserComment());

            teamNumber = fi.getTeamNumber();
            gameNumber = fi.getMatchNumber();

            btnSubmit.setOnClickListener(this);
        }

        btnBack.setOnClickListener(this);
    }

    private void setControlPanels() {
        if (fi.getCpRotation() == 1)
            imgPCRC.setImageResource(User.controlPanelRotation[0]);
        else
            imgPCRC.setVisibility(View.INVISIBLE);

        if (fi.getCpPosition() == 1)
            imgCPPC.setImageResource(User.controlPanelPosition[0]);
        else
            imgCPPC.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                btnSubmit.setEnabled(false);
                btnBack.setEnabled(false);
                if (fi.getTeamNumber() != null && !fi.getTeamNumber().equals("")) {
                    dbRef = dbRef.child(teamNumber);
                    onSubmit();
                }
                break;

            case R.id.btnBack:
                i = new Intent(SubmissionActivity.this, GameFormActivity.class);

                if (c != null && !c.isEmpty())
                    if (adapter.getCycles() != null && !adapter.getCycles().isEmpty())
                        c = adapter.getCycles();

                i.putExtra(Keys.FINISH_PC, c);
                i.putExtra(Keys.FORM_INFO, fi);
                finish();
                startActivity(i);
        }
    }

    private void onSubmit() {
        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int dbGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
                GeneralFunctions.onSubmit(dbRef, fi, c);
                User.currentGame = fi.getMatchNumber() + 1;

                if (User.currentGame > dbGame) {
                    dbGame = User.currentGame;
                    User.databaseReference.child(Keys.CURRENT_GAME).setValue(dbGame);

                } else if (dbGame - User.currentGame > 1) {
                    OverrideExistingMatchAlertDialog dialog = new OverrideExistingMatchAlertDialog();
                    dialog.show(getSupportFragmentManager(), "Override Form");
                }

                if (isValid) {
                    if (c != null && !c.isEmpty()) {
                        if (adapter.getCycles() != null && !adapter.getCycles().isEmpty()) {
                            c = adapter.getCycles();
                        } else {
                            MissingTeamNumberAlertDialog alertDialog = new MissingTeamNumberAlertDialog();
                            alertDialog.show(getSupportFragmentManager(), "Missing Team Number");
                        }
                    }
                    finishedForm = new Intent(SubmissionActivity.this, DrawerActivity.class);
                    finish();
                    startActivity(finishedForm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        btnSubmit.setEnabled(true);
    }
}
