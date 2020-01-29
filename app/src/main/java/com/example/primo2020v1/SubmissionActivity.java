package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.EndGameFragment;
import com.example.primo2020v1.Fragments.FinishFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.FormInfo;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Map;

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SubmissionActivity";
    private ListView lvCycles;
    private ImageView imgEndGame, imgFinish;
    private Button btnSubmit, btnBack;
    private Switch switchPC, switchCPColor;
    private String teamNumber;
    private int gameNumber;
    private CyclesAdapter adapter;

    private Intent intent, finishedForm;
    private FormInfo fi;
    private ArrayList<Cycle> c;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        dbRef = User.databaseReference.child("Team");

        lvCycles = findViewById(R.id.lvCycles);
        imgFinish = findViewById(R.id.imgFinish);
        imgEndGame = findViewById(R.id.imgEndGame);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
        switchPC = findViewById(R.id.switchCP);
        switchCPColor = findViewById(R.id.switchCPColor);

        intent = getIntent();
        if (intent.hasExtra(Keys.FORM_INFO) && intent.hasExtra(Keys.FINISH_PC)) {
            fi = (FormInfo) intent.getParcelableExtra(Keys.FORM_INFO);
            c = intent.getParcelableArrayListExtra(Keys.FINISH_PC);

            if (c.get(0).getTotalPC() == 0)
                c.remove(0);

            if (!c.isEmpty()) {
                adapter = new CyclesAdapter(getApplicationContext(), R.layout.custom_submission_form, c);
                lvCycles.setAdapter(adapter);
                Log.d(TAG, "onCreate: " + c.toString());

            } else {
                Log.d(TAG, "onCreate: Cycles = NULL");
            }

            Log.d(TAG, "onCreate: EngGame Image Id: " + imgEndGame + "; Finish Image Id: " + imgFinish);
            imgEndGame.setImageResource(fi.getEndGame());
            imgFinish.setBackgroundColor(fi.getFinish());
            switchPC.setChecked(fi.isControlPanel());
            switchCPColor.setChecked(fi.isControlPanelColor());
            teamNumber = fi.getTeamNumber();
            gameNumber = fi.getGameNumber();
        }

        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                onSubmit();
                resetForm();
                if (!c.isEmpty())
                    if (!adapter.getCycles().isEmpty())
                        c = adapter.getCycles();

                finishedForm = new Intent(SubmissionActivity.this, MainActivity.class);
                finish();
                startActivity(finishedForm);
                break;

            case R.id.btnBack:
                Intent i = new Intent(SubmissionActivity.this, GameFormActivity.class);

                if (!c.isEmpty()) {
                    if (!adapter.getCycles().isEmpty())
                        c = adapter.getCycles();

                    i.putExtra(Keys.FINISH_PC, c);
                    Log.d(TAG, "onClick: " + c);
                }

                i.putExtra(Keys.FORM_INFO, fi);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    private void onSubmit() {
        dbRef = dbRef.child(teamNumber).child(Integer.toString(gameNumber));
        Map<String, Object> formInfo = GeneralFunctions.getMap(fi);

        for (int i = 0; i < c.size(); i++){
            Map<String, Object> cycle = GeneralFunctions.getMap(c.get(i));
            Log.d(TAG, "onSubmit: " + cycle.toString());
            dbRef.child("Cycles " + i).setValue(cycle);
        }

        dbRef.child("FormInfo").setValue(formInfo);
    }

    private void resetForm() {
        MatchSettingsFragment.teamNumber = "";
        MatchSettingsFragment.spnIndex = 0;
        MatchSettingsFragment.gameNumber = 0;

        PowerCellsFragment.phase = false;
        for (int i = 0; i < PowerCellsFragment.positions.length; i++) {
            PowerCellsFragment.positions[i] = 0;
        }

        ControlPanelFragment.state1 = false;
        ControlPanelFragment.state2 = false;

        EndGameFragment.imageIndex = 0;

        FinishFragment.imageIndex = 0;
        FinishFragment.text = "";
    }
}
