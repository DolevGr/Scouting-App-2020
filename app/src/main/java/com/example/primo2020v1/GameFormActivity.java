package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.primo2020v1.libs.ButtonsFragment;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.MatchManager;
import com.example.primo2020v1.libs.User;

public class GameFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    ButtonsFragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Spinner teamSpinner;
    ArrayAdapter<CharSequence> teamAdapter;
    Button btnNext, btnTest, btnBack, btnCycle, btnTeleAuto;
    EditText edGameNumber, edTeamNumber;
    public TextView tvPowerCellsInner, tvPowerCellsOuter, tvPowerCellsLower, tvPowerCellsMissed;
    public SeekBar sbPowerCellsInner, sbPowerCellsOuter, sbPowerCellsLower, sbPowerCellsMissed;

    String optionSelected = "", gameNumber = "", teamNumber = "";
    int optionSelectedIndex = 0, layoutIndex = 0, cycles = 0;

    //Tele: true; Auto: false
    boolean phase = false;

    MatchManager matchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_match);
        //setTitle("title");


//        fragment = new ButtonsFragment();
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.frgButtons, fragment);
//        fragmentTransaction.commit();

        edGameNumber = findViewById(R.id.edGameNumber);
        edTeamNumber = findViewById(R.id.edTeamNumber);

        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        teamSpinner = findViewById(R.id.spnTeam);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);

        edTeamNumber.setText(teamNumber);
        edGameNumber.setText(gameNumber);

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        teamSpinner.setOnItemSelectedListener(this);

//        btnTest = findViewById(R.id.btnTest);
//        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnNext:
                matchManager = new MatchManager(edTeamNumber.getText().toString().trim(),
                        optionSelected, User.currentGame, GameFormActivity.this);
                setContentView(R.layout.power_cells_form);

                tvPowerCellsInner = (TextView) findViewById(R.id.tvPowerCellsInner);
                tvPowerCellsOuter = (TextView) findViewById(R.id.tvPowerCellsOuter);
                tvPowerCellsLower = (TextView) findViewById(R.id.tvPowerCellsLower);
                tvPowerCellsMissed = (TextView) findViewById(R.id.tvPowerCellsMissed);
                btnCycle = (Button) findViewById(R.id.btnCycle);
                btnTeleAuto = (Button) findViewById(R.id.btnTeleAuto);

                sbPowerCellsInner = (SeekBar) findViewById(R.id.sbPowerCellsInner);
                sbPowerCellsOuter = (SeekBar) findViewById(R.id.sbPowerCellsOuter);
                sbPowerCellsLower = (SeekBar) findViewById(R.id.sbPowerCellsLower);
                sbPowerCellsMissed = (SeekBar) findViewById(R.id.sbPowerCellsMissed);
                matchManager.setMaxSeekBars();

                sbPowerCellsMissed.setOnSeekBarChangeListener(this);
                sbPowerCellsLower.setOnSeekBarChangeListener(this);
                sbPowerCellsOuter.setOnSeekBarChangeListener(this);
                sbPowerCellsInner.setOnSeekBarChangeListener(this);
                btnCycle.setOnClickListener(this);
                btnTeleAuto.setOnClickListener(this);

                Log.d("Next Button ", "onClick: Next Stage in GameForm");
                Toast.makeText(this, "BtnNext ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.spnTeam:
                Log.d("Spinner Option ", "onClick: " + optionSelected);
                Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnTest:
                /*User.currentGame++;
                GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);*/
                Toast.makeText(this,"Current Game: " + User.currentGame, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_before_match);
                break;

            case R.id.btnBack:
                startActivity(new Intent(GameFormActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.btnPowerCells:
                layoutIndex = 0;
                setContentView(R.layout.power_cells_form);
                break;

            case R.id.btnControlPanel:
                layoutIndex = 1;
                break;

            case R.id.btnCycle:
                cycles++;
                matchManager.endOfCycle(phase);
                matchManager.addCyclesToDB(cycles);
                matchManager.resetSeekBars();
                break;

            case R.id.btnTeleAuto:
                if(btnTeleAuto.getText().equals("Auto")) {
                    btnTeleAuto.setText("Tele");
                    phase = true;
                } else {
                    btnTeleAuto.setText("Auto");
                    phase = false;
                }
                break;

            default:
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        optionSelectedIndex = i;
        optionSelected = (String) teamSpinner.getSelectedItem();

        Log.d("Spinner Option ", "onClick: " + optionSelected);
        Toast.makeText(this, optionSelected + "", Toast.LENGTH_SHORT).show();

        GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        optionSelected = (String) teamSpinner.getItemAtPosition(optionSelectedIndex);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            if(progress > matchManager.getMax(seekBar))
                seekBar.setProgress(matchManager.getMax(seekBar));
        }

        matchManager.updateSeekBarsText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}
