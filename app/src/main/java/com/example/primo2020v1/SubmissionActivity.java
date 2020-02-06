package com.example.primo2020v1;

import android.content.Intent;
import android.graphics.Color;
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

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SubmissionActivity";
    private ListView lvCycles;
    private ImageView imgEndGame, imgFinish;
    private Button btnSubmit, btnBack;
    private ImageView imgPCnormal, imgCPcolor, imgTicket, imgCrash, imgDefence;
    private TextView tvComment, tvTicket, tvCrash, tvDefence;
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
        imgDefence = findViewById(R.id.imgDefence);
        tvDefence = findViewById(R.id.tvDefence);
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
            }

            imgEndGame.setImageResource(User.endGameImages[fi.getEndGame()]);
            imgFinish.setImageDrawable(getResources().getDrawable(User.finishImages[fi.getFinish()]));
            imgTicket.setColorFilter(User.finishTickets[fi.getTicket()]);
            tvTicket.setTextColor(fi.getTicket() == 1 ? Color.BLACK : Color.WHITE);
            imgCrash.setColorFilter(User.finishCrash[fi.getCrash()]);
            tvCrash.setTextColor(fi.getCrash() == 1 ? Color.BLACK : Color.WHITE);
            imgDefence.setColorFilter(User.finishDefence[fi.getDefence()]);
            tvDefence.setTextColor(fi.getDefence() == 1 ? Color.BLACK : Color.WHITE);
            tvComment.setText(fi.getUserComment());
            setControlPanels();

            teamNumber = fi.getTeamNumber();
            gameNumber = fi.getGameNumber();

            btnSubmit.setOnClickListener(this);
        }

        btnBack.setOnClickListener(this);
    }

    private void setControlPanels() {
        isCPnormal = fi.isControlPanel();
        if (!isCPnormal)
            imgPCnormal.setVisibility(View.INVISIBLE);
        imgPCnormal.setColorFilter(getResources().getColor(R.color.mainBlue));

        isCPcolor = fi.isControlPanelColor();
        if (!isCPcolor)
            imgCPcolor.setVisibility(View.INVISIBLE);
        imgCPcolor.setColorFilter(getResources().getColor(R.color.mainBlue));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (fi.getTeamNumber() != null && !fi.getTeamNumber().equals("")) {
                    dbRef = dbRef.child(teamNumber).child(Integer.toString(gameNumber));
                    onSubmit();

                    if (c != null && !c.isEmpty())
                        if (adapter.getCycles() != null && !adapter.getCycles().isEmpty())
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
                i.putExtra(Keys.FORM_INFO, fi);
                finish();
                startActivity(i);
        }
    }

    private void onSubmit() {
        GeneralFunctions.onSubmit(dbRef, fi, c);
        User.currentGame = fi.getGameNumber() + 1;
        User.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int dbGame;
                dbGame = Integer.parseInt(dataSnapshot.child(Keys.CURRENT_GAME).getValue().toString());

                if (User.currentGame != dbGame) {
                    dbGame = User.currentGame;
                    User.databaseReference.child(Keys.CURRENT_GAME).setValue(dbGame);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
