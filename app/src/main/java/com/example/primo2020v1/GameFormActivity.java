package com.example.primo2020v1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.EndGameFragment;
import com.example.primo2020v1.Fragments.FinishFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;
import com.example.primo2020v1.libs.Cycle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GameFormActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ControlPanelFragment.ControlPanelListener, MatchSettingsFragment.MatchSettingsListener,
        PowerCellsFragment.PowerCellsListener, EndGameFragment.EndGameListener, FinishFragment.FinishListener {
    private static final String TAG = "GameFormActivity";

    private MatchSettingsFragment matchSettingsFragment;

    public Button btnTest, btnToSubmission;
    BottomNavigationView bnvForm;
    Fragment selectedFragment;

    //index 0: Missed; 1: Lower; 2: Outer; 3: Inner
    private ArrayList<Cycle> cycles;
    public String spnOptionSelected = "", teamNumber = "";
    int spnOptionSelectedIndex = 0, gameNumber = 0, numOfCycles,
        teamState;
    boolean controlPanel, controlPanelColor,
        didPark, didClimb, didBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);
        //setTitle("title");

        matchSettingsFragment = new MatchSettingsFragment();

        btnToSubmission = (Button) findViewById(R.id.btnToSubmission);
        bnvForm = (BottomNavigationView) findViewById(R.id.bottomNavFormBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForm, matchSettingsFragment).commit();
        bnvForm.setOnNavigationItemSelectedListener(this);

        cycles = new ArrayList<>();
        numOfCycles = 0;
    }


    //Bottom Navigation View
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navPowerCells:
                selectedFragment = new PowerCellsFragment();
                break;

            case R.id.navControlPanel:
                selectedFragment = new ControlPanelFragment();
                break;

            case R.id.navEndGame:
                selectedFragment = new EndGameFragment();
                break;

            case R.id.navFinishForm:
                selectedFragment = new FinishFragment();
                break;

            default:
                selectedFragment = new MatchSettingsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentForm, selectedFragment).commit();

        return true;
    }


    @Override
    public void getDataMatchSettings(String teamSelected, int position, int gameNumber) {
        teamNumber = teamSelected;
        this.gameNumber = gameNumber;
        spnOptionSelectedIndex = position;
    }

    @Override
    public void getDataPowerCells(int pcMissed, int pcLower, int pcOuter, int pcInner, boolean phase) {
        if (pcMissed + pcLower + pcOuter + pcOuter > 0)
            cycles.add(new Cycle(pcMissed, pcLower, pcOuter, pcInner, phase));
        numOfCycles++;

//        Toast.makeText(this, "Last Cycle: " + cycles.get(cycles.size()-1).toString(), Toast.LENGTH_LONG).show();
//        Log.d(TAG, "getDataPowerCells: " + cycles.get(cycles.size()-1).toString());
    }

    @Override
    public void getDataControlPanel(boolean switchControlPanelState, boolean switchControlPanelColorState) {
        controlPanel = switchControlPanelState;
        controlPanelColor = switchControlPanelColorState;

//        Toast.makeText(this, "Switched: normal: " + controlPanel + " By color: " + controlPanelColor ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataEndGame(boolean parked, boolean climbed, boolean balanced) {
        didPark = parked;
        didClimb = climbed;
        didBalance = balanced;
    }

    @Override
    public void getDataFinish(int state) {
        this.teamState = state;
    }
}
