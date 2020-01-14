package com.example.primo2020v1;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.MatchManager;
import com.example.primo2020v1.libs.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GameFormActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private static final String TAG = "HHHHHHHHHHHHHHHn";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public Spinner teamSpinner;
    public ArrayAdapter<CharSequence> teamAdapter;
    public Button btnTest, btnCycle, btnTeleAuto;
    public EditText edGameNumber, edTeamNumber;
    public TextView tvPowerCellsInner, tvPowerCellsOuter, tvPowerCellsLower, tvPowerCellsMissed;
    public SeekBar sbPowerCellsInner, sbPowerCellsOuter, sbPowerCellsLower, sbPowerCellsMissed;
    BottomNavigationView bnvForm;

    public String spnOptionSelected = "", gameNumber = "", teamNumber = "";
    int optionSelectedIndex = 0, cycles = 0;

    //Tele: true; Auto: false
    boolean phase = false;

    MatchManager matchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);
        //setTitle("title");


        bnvForm = (BottomNavigationView) findViewById(R.id.bottomNavFormBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForm, new MatchSettingsFragment()).commit();
        bnvForm.setOnNavigationItemReselectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.spnTeam:
                Log.d("Spinner Option ", "onClick: " + spnOptionSelected);
                Toast.makeText(this, spnOptionSelected + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnTest:
                /*User.currentGame++;
                GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);*/
                Toast.makeText(this,"Current Game: " + User.currentGame, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_before_match);
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
                    btnTeleAuto.setBackgroundColor(getResources().getColor(R.color.TeleColor));
                    phase = true;
                } else {
                    btnTeleAuto.setText("Auto");
                    btnTeleAuto.setBackgroundColor(getResources().getColor(R.color.AutoColor));
                    phase = false;
                }
                break;

            default:
                break;

        }
    }


    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        optionSelectedIndex = i;
        spnOptionSelected = (String) teamSpinner.getSelectedItem();

        Log.d("Spinner Option ", "onClick: " + spnOptionSelected);
        Toast.makeText(this, spnOptionSelected + "", Toast.LENGTH_SHORT).show();

        GeneralFunctions.updateTeamSpinner(optionSelectedIndex, edGameNumber, edTeamNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spnOptionSelected = (String) teamSpinner.getItemAtPosition(optionSelectedIndex);
    }


    //SeekBars
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


    //Bottom Navigation View
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;

        switch (menuItem.getItemId()){
            case R.id.navMatchSettings:
                selectedFragment = new MatchSettingsFragment();
                break;

            case R.id.navPowerCells:
                selectedFragment = new PowerCellsFragment();
                break;

            case R.id.navControlPanel:
                selectedFragment = new ControlPanelFragment();
                break;

            default:
                selectedFragment = new MatchSettingsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentForm, selectedFragment).commit();


        if(selectedFragment instanceof MatchSettingsFragment){
            setupMatchSettings();
            setupMatchSettingsListeners();

            teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.support_simple_spinner_dropdown_item);
            teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            teamSpinner.setAdapter(teamAdapter);

            matchManager = new MatchManager(spnOptionSelected, Integer.toString(optionSelectedIndex),
                    User.currentGame, GameFormActivity.this);
        } else if(selectedFragment instanceof PowerCellsFragment){
            setupPowerCells();
            setupPowerCellsListeners();

            matchManager.setMaxSeekBars();
        }
    }


    //Setup
    private void setupMatchSettings(){

        //btnTest = findViewById(R.id.btnTest);
    }

    private void setupMatchSettingsListeners(){
        teamSpinner.setOnItemSelectedListener(this);

        //btnTest.setOnClickListener(this);
    }

    private void setupPowerCells(){
        tvPowerCellsInner = (TextView) findViewById(R.id.tvPowerCellsInner);
        tvPowerCellsOuter = (TextView) findViewById(R.id.tvPowerCellsOuter);
        tvPowerCellsLower = (TextView) findViewById(R.id.tvPowerCellsLower);
        tvPowerCellsMissed = (TextView) findViewById(R.id.tvPowerCellsMissed);

        sbPowerCellsInner = (SeekBar) findViewById(R.id.sbPowerCellsInner);
        sbPowerCellsOuter = (SeekBar) findViewById(R.id.sbPowerCellsOuter);
        sbPowerCellsLower = (SeekBar) findViewById(R.id.sbPowerCellsLower);
        sbPowerCellsMissed = (SeekBar) findViewById(R.id.sbPowerCellsMissed);

        btnCycle = (Button) findViewById(R.id.btnCycle);
        btnTeleAuto = (Button) findViewById(R.id.btnTeleAuto);
    }

    public void setupPowerCellsListeners(){
        sbPowerCellsMissed.setOnSeekBarChangeListener(this);
        sbPowerCellsLower.setOnSeekBarChangeListener(this);
        sbPowerCellsOuter.setOnSeekBarChangeListener(this);
        sbPowerCellsInner.setOnSeekBarChangeListener(this);

        btnCycle.setOnClickListener(this);
        btnTeleAuto.setOnClickListener(this);

    }
}
