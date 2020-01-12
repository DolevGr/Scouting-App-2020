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
import com.example.primo2020v1.libs.User;

public class GameFormActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    ButtonsFragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Spinner teamSpinner;
    ArrayAdapter<CharSequence> teamAdapter;
    Button btnNext, btnTest, btnBack, btnCycle;
    EditText edGameNumber, edTeamNumber;
    TextView tvPowerCellsInner, tvPowerCellsOuter, tvPowerCellsLower, tvPowerCellsMissed;
    SeekBar sbPowerCellsInner, sbPowerCellsOuter, sbPowerCellsLower, sbPowerCellsMissed;

    String optionSelected = "", gameNumber = "", teamNumber = "";
    int optionSelectedIndex = 0, layoutIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_match);
        //setTitle("title");


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
                setContentView(R.layout.power_cells_form);

                tvPowerCellsInner = (TextView) findViewById(R.id.tvPowerCellsInner);
                tvPowerCellsOuter = (TextView) findViewById(R.id.tvPowerCellsOuter);
                tvPowerCellsLower = (TextView) findViewById(R.id.tvPowerCellsLower);
                tvPowerCellsMissed = (TextView) findViewById(R.id.tvPowerCellsMissed);
                btnCycle = (Button) findViewById(R.id.btnCycle);

                sbPowerCellsInner = (SeekBar) findViewById(R.id.sbPowerCellsInner);
                sbPowerCellsOuter = (SeekBar) findViewById(R.id.sbPowerCellsOuter);
                sbPowerCellsLower = (SeekBar) findViewById(R.id.sbPowerCellsLower);
                sbPowerCellsMissed = (SeekBar) findViewById(R.id.sbPowerCellsMissed);
                setMaxSeekBars();

                sbPowerCellsMissed.setOnSeekBarChangeListener(this);
                sbPowerCellsLower.setOnSeekBarChangeListener(this);
                sbPowerCellsOuter.setOnSeekBarChangeListener(this);
                sbPowerCellsInner.setOnSeekBarChangeListener(this);
                btnCycle.setOnClickListener(this);

                Log.d("Next Button ", "onClick: Next Stage in GameForm");
                Toast.makeText(this, "BtnNext ", Toast.LENGTH_SHORT).show();

//                fragment = new ButtonsFragment();
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.frgButtons, fragment);
//                fragmentTransaction.commit();
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
                sbPowerCellsMissed.setProgress(0);
                sbPowerCellsLower.setProgress(0);
                sbPowerCellsOuter.setProgress(0);
                sbPowerCellsInner.setProgress(0);
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
            setMaxSeekBars();
            if(progress > getMax(seekBar))
                seekBar.setProgress(getMax(seekBar));
        }

        updateSeekBarsText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    private int getPowerCellsFromSeekBars(){
        return sbPowerCellsMissed.getProgress()
                + sbPowerCellsLower.getProgress()
                + sbPowerCellsOuter.getProgress()
                + sbPowerCellsInner.getProgress();
    }

    private int getMax(SeekBar sb){
        return 5 - getPowerCellsFromSeekBars() + sb.getProgress();
    }

    private void setMaxSeekBars(){
        sbPowerCellsMissed.setMax(getMax(sbPowerCellsMissed));
        sbPowerCellsMissed.setMax(5);

        sbPowerCellsLower.setMax(getMax(sbPowerCellsLower));
        sbPowerCellsLower.setMax(5);

        sbPowerCellsOuter.setMax(getMax(sbPowerCellsOuter));
        sbPowerCellsOuter.setMax(5);

        sbPowerCellsInner.setMax(getMax(sbPowerCellsInner));
        sbPowerCellsInner.setMax(5);
    }

    public void updateSeekBarsText(){
        tvPowerCellsMissed.setText(Integer.toString(sbPowerCellsMissed.getProgress()));
        tvPowerCellsLower.setText(Integer.toString(sbPowerCellsLower.getProgress()));
        tvPowerCellsOuter.setText(Integer.toString(sbPowerCellsOuter.getProgress()));
        tvPowerCellsInner.setText(Integer.toString(sbPowerCellsInner.getProgress()));
    }
}
