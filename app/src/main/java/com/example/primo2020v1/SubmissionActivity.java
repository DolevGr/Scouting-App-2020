package com.example.primo2020v1;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.AlertDialogs.MissingTeamNumberAlertDialog;
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
import java.util.Map;

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SubmissionActivity";
    private ListView lvCycles;
    private ImageView imgEndGame, imgFinish;
    private Button btnSubmit, btnBack;
    private ImageView imgPCnormal, imgCPcolor, imgTicket, imgCrash;
    private TextView tvComment, tvTicket, tvCrash;
    private String teamNumber;
    private int gameNumber;
    private CyclesAdapter adapter;
    private boolean isCPnormal, isCPcolor;

    private Intent intent, finishedForm, i;
    private FormInfo fi;
    private ArrayList<Cycle> c;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        dbRef = User.databaseReference.child("Teams");

        lvCycles = findViewById(R.id.lvCycles);
        imgTicket = findViewById(R.id.imgTicket);
        tvTicket = findViewById(R.id.tvTicket);
        imgCrash = findViewById(R.id.imgDidCrash);
        tvCrash = findViewById(R.id.tvCrash);
        imgFinish = findViewById(R.id.imgFinish);
        imgEndGame = findViewById(R.id.imgEndGame);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
        imgPCnormal = findViewById(R.id.imgCPnormal);
        imgCPcolor = findViewById(R.id.imgCPcolor);
        tvComment = findViewById(R.id.tvComment);

        intent = getIntent();
        if (intent.hasExtra(Keys.FORM_INFO) && intent.hasExtra(Keys.FINISH_PC)) {
            fi = intent.getParcelableExtra(Keys.FORM_INFO);
            c = intent.getParcelableArrayListExtra(Keys.FINISH_PC);

            if (c != null && !c.isEmpty()) {
                adapter = new CyclesAdapter(getApplicationContext(), R.layout.custom_submission_form, c);
                lvCycles.setAdapter(adapter);
                Log.d(TAG, "onCreate: " + c.toString());

            } else {
                Log.d(TAG, "onCreate: Cycles = NULL");
            }

            Log.d(TAG, "onCreate: EngGame Image Id: " + imgEndGame + "; Finish Image Id: " + imgFinish);
            imgEndGame.setImageResource(fi.getEndGame());

            imgFinish.setImageDrawable(getResources().getDrawable(fi.getFinish()));
            imgTicket.setColorFilter(fi.getTicket());
            tvTicket.setTextColor(fi.getTicket() != Color.BLACK ? Color.BLACK : Color.WHITE);
            imgCrash.setColorFilter(fi.getCrash() ? Color.GREEN : Color.RED);
            tvTicket.setTextColor(fi.getCrash() ? Color.BLACK : Color.WHITE);

            isCPnormal = fi.isControlPanel();
            if (!isCPnormal) {
                imgPCnormal.setVisibility(View.INVISIBLE);
            }
            imgPCnormal.setColorFilter(getResources().getColor(R.color.mainBlue));

            isCPcolor = fi.isControlPanelColor();
            if (!isCPcolor) {
                imgCPcolor.setVisibility(View.INVISIBLE);
            }
            imgCPcolor.setColorFilter(getResources().getColor(R.color.mainBlue));

            teamNumber = fi.getTeamNumber();
            gameNumber = fi.getGameNumber();
            tvComment.setText(fi.getUserComment());
        }

        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (fi.getTeamNumber() != null && !fi.getTeamNumber().equals("")) {
                    onSubmit();
                    GeneralFunctions.resetForm();
                    if (!c.isEmpty())
                        if (!adapter.getCycles().isEmpty())
                            c = adapter.getCycles();

                    finishedForm = new Intent(SubmissionActivity.this, MainActivity.class);
                    finish();
                    startActivity(finishedForm);
                } else {
                    MissingTeamNumberAlertDialog alertDialog = new MissingTeamNumberAlertDialog();
                    alertDialog.show(getSupportFragmentManager(), "Missing Team Number");
                }

                break;

            case R.id.btnBack:
                i = new Intent(SubmissionActivity.this, GameFormActivity.class);

                if (c != null && !c.isEmpty())
                    if (adapter.getCycles() != null && !adapter.getCycles().isEmpty())
                        c = adapter.getCycles();

                i.putExtra(Keys.FINISH_PC, c);
                Log.d(TAG, "onClick: " + c);

                i.putExtra(Keys.FORM_INFO, fi);
                finish();
                startActivity(i);
        }
    }

    private void onSubmit() {
        int totalCycles = c.size(), totalScore = 0,
                totalPCmissed = 0, totalPClower = 0, totalPCouter = 0, totalPCinner = 0;

        dbRef = dbRef.child(teamNumber).child(Integer.toString(gameNumber));
        clearMatch();
        Map<String, Object> formInfo = GeneralFunctions.getMap(fi);

        dbRef.child("CommittedBy").setValue(User.username);

        for (int i = 0; i < c.size(); i++) {
            Map<String, Object> cycle = GeneralFunctions.getMap(c.get(i));
            dbRef.child("Cycles " + (i + 1)).setValue(cycle);
            Log.d(TAG, "onSubmit: " + cycle.toString());

            totalPCmissed += c.get(i).pcMissed;
            totalPClower += c.get(i).pcLower;
            totalPCouter += c.get(i).pcOuter;
            totalPCinner += c.get(i).pcInner;

            totalScore += c.get(i).getScore();
        }

        dbRef.child("TotalMissed").setValue(totalPCmissed);
        dbRef.child("TotalLower").setValue(totalPClower);
        dbRef.child("TotalOuter").setValue(totalPCouter);
        dbRef.child("TotalInner").setValue(totalPCinner);

        dbRef.child("TotalScore").setValue(totalScore);
        dbRef.child("NumberOfCycles").setValue(totalCycles);

        dbRef.child("ControlPanel").setValue(fi.isControlPanel());
        dbRef.child("ControlPanelColor").setValue(fi.isControlPanelColor());
        dbRef.child("EndGame").setValue(fi.getEndGame());
        dbRef.child("Finish").setValue(fi.getFinish());
        dbRef.child("Ticket").setValue(fi.getTicket());
        dbRef.child("DidCrash").setValue(fi.getCrash());

        if (fi.getUserComment() != null && !fi.getUserComment().equals(""))
            dbRef.child("Comment").setValue(fi.getUserComment());
        else
            dbRef.child("Comment").setValue("Empty comment");

        Log.d(TAG, "onSubmit: " + formInfo.toString());

        User.currentGame = fi.getGameNumber() + 1;
        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int dbGame;

                dbGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());
                Log.d(TAG, "onDataChange: DB Game Number: " + dbGame +
                        "\n Form Game Number: " + fi.getGameNumber());

                if (User.currentGame != dbGame) {
                    dbGame = User.currentGame;
                    User.databaseReference.child(Keys.CURRENT_GAME).setValue(dbGame);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void clearMatch() {
        dbRef.setValue("Clearing Match");
    }
}
