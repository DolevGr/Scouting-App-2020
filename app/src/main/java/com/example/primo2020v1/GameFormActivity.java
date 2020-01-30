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
import java.util.HashMap;
import java.util.Map;

public class GameFormActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ControlPanelFragment.ControlPanelListener, MatchSettingsFragment.MatchSettingsListener,
        PowerCellsFragment.PowerCellsListener, EndGameFragment.EndGameListener, FinishFragment.FinishListener {
    private static final String TAG = "GameFormActivity";

    private MatchSettingsFragment matchSettingsFragment;
    private Intent intent;
    BottomNavigationView bnvForm;
    Fragment selectedFragment;

    private ArrayList<Cycle> cycles;
    private FormInfo formInfo;
    public String teamNumber = "";
    int spnOptionSelectedIndex = 0, gameNumber = 1, numOfCycles,
            endGameImageId = R.drawable.ic_empty, finishImgId = R.drawable.ic_won;
    boolean isControlPanelNormal, isControlPanelColor;
    CharSequence text;
    private Map<Integer, Fragment> fragsMap;


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
            isControlPanelNormal = formInfo.isControlPanel();
            isControlPanelColor = formInfo.isControlPanelColor();
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
            numOfCycles = 0;
        }

        Log.d(TAG, "onCreate: " + cycles.toString());


        fragsMap = new HashMap<>();
        fragsMap.put(R.id.navPowerCells, new PowerCellsFragment());
        fragsMap.put(R.id.navControlPanel, new ControlPanelFragment());
        fragsMap.put(R.id.navEndGame, new EndGameFragment());
        fragsMap.put(R.id.navFinishForm, new FinishFragment());
        fragsMap.put(R.id.navMatchSettings, new MatchSettingsFragment());
    }


    //Bottom Navigation View
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        selectedFragment = fragsMap.get(id);
        if (selectedFragment == null)
            selectedFragment = fragsMap.get(R.id.navMatchSettings);

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
        isControlPanelNormal = cpIntent.getBooleanExtra(Keys.CP_NORMAL, false);
        isControlPanelColor = cpIntent.getBooleanExtra(Keys.CP_COLOR, false);
    }

    @Override
    public void getDataPowerCells(Intent pcIntent) {
        ArrayList<Cycle> c = pcIntent.getParcelableArrayListExtra(Keys.PC_CYCLE);

        if (c != null && !c.isEmpty()) {
            cycles.addAll(c);
        }
    }

    @Override
    public void getDataEndGame(Intent egIntent) {
        endGameImageId = egIntent.getIntExtra(Keys.EG_IMG_ID, R.drawable.ic_empty);
    }

    @Override
    public void getDataFinish(Intent finishIntent) {
        finishImgId = finishIntent.getIntExtra(Keys.FINISH_TEAM, R.drawable.ic_won);
        text = finishIntent.getCharSequenceExtra(Keys.FINISH_TEXT);
    }

    @Override
    public FormInfo setFormInfo() {
        formInfo = new FormInfo(teamNumber, gameNumber,
                isControlPanelNormal, isControlPanelColor,
                endGameImageId, finishImgId, text);
        return formInfo;
    }
}
