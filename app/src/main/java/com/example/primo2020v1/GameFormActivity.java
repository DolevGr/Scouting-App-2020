package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Fragments.ControlPanelFragment;
import com.example.primo2020v1.Fragments.EndGameFragment;
import com.example.primo2020v1.Fragments.FinishFragment;
import com.example.primo2020v1.Fragments.MatchSettingsFragment;
import com.example.primo2020v1.Fragments.PowerCellsFragment;
import com.example.primo2020v1.libs.Cycle;
import com.example.primo2020v1.libs.FormInfo;
import com.example.primo2020v1.libs.Keys;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GameFormActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ControlPanelFragment.ControlPanelListener, MatchSettingsFragment.MatchSettingsListener,
        PowerCellsFragment.PowerCellsListener, EndGameFragment.EndGameListener, FinishFragment.FinishListener {
    private static final String TAG = "GameFormActivity";

    private MatchSettingsFragment matchSettingsFragment;
    private Intent intent;
    BottomNavigationView bnvForm;
    Fragment selectedFragment;

    //index 0: Missed; 1: Lower; 2: Outer; 3: Inner
    private ArrayList<Cycle> cycles;
    private FormInfo formInfo;
    public String teamNumber = "";
    int spnOptionSelectedIndex = 0, gameNumber = 1, numOfCycles,
            endGameImageId = R.drawable.ic_empty, finishImgId = 0 /*CHANGE to R.drawable.image*/;
    boolean controlPanel, controlPanelColor;
    CharSequence text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        intent = getIntent();
        matchSettingsFragment = new MatchSettingsFragment();

        bnvForm = (BottomNavigationView) findViewById(R.id.bottomNavFormBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForm, matchSettingsFragment).commit();
        bnvForm.setOnNavigationItemSelectedListener(this);

        if (intent.hasExtra(Keys.FORM_INFO)) {
            formInfo = intent.getParcelableExtra(Keys.FORM_INFO);
            controlPanel = formInfo.isControlPanel();
            controlPanelColor = formInfo.isControlPanelColor();
            endGameImageId = formInfo.getEndGame();
            finishImgId = formInfo.getFinish();
            text = formInfo.getText();
        }

        if (intent.hasExtra(Keys.FINISH_PC)) {
            cycles = intent.getParcelableArrayListExtra(Keys.FINISH_PC);
            Log.d(TAG, "onCreate: " + cycles.toString());

            if (!cycles.isEmpty())
                numOfCycles = cycles.size();
            else
                numOfCycles = 0;
        } else {
            cycles = new ArrayList<>();
            cycles.add(new Cycle(0, 0, 0, 0, false));
            numOfCycles = 0;
        }

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
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentForm, selectedFragment).commit();

        return true;
    }


    @Override
    public void getDataMatchSettings(Intent msIntent) {
        teamNumber = msIntent.getStringExtra(Keys.MS_TEAM);
        gameNumber = msIntent.getIntExtra(Keys.MS_NUMBER, 1);
        spnOptionSelectedIndex = msIntent.getIntExtra(Keys.MS_TEAM_INDEX, 0);
    }

    @Override
    public ArrayList<Cycle> getCyclesFinish() {
        return cycles;
    }

    @Override
    public void getDataControlPanel(Intent cpIntent) {
        controlPanel = cpIntent.getBooleanExtra(Keys.CP_NORMAL, false);
        controlPanelColor = cpIntent.getBooleanExtra(Keys.CP_COLOR, false);
    }

    @Override
    public void getDataPowerCells(Intent pcIntent) {
        ArrayList<Cycle> c = pcIntent.getParcelableArrayListExtra(Keys.PC_CYCLE);

        if(!c.isEmpty()){
            cycles.addAll(c);
        }
    }

    @Override
    public void getDataEndGame(Intent egIntent) {
        endGameImageId = egIntent.getIntExtra(Keys.EG_IMG_ID, R.drawable.ic_empty);
    }

    @Override
    public void getDataFinish(Intent finishIntent) {
        finishImgId = finishIntent.getIntExtra(Keys.FINISH_TEAM, 0);
        text = finishIntent.getCharSequenceExtra(Keys.FINISH_TEXT);
    }

    @Override
    public FormInfo setFormInfo() {
        formInfo = new FormInfo(teamNumber, gameNumber,
                controlPanel, controlPanelColor,
                endGameImageId, finishImgId, text);
        return formInfo;
    }
}
