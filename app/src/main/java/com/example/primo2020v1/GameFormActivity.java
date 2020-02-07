package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.AlertDialogs.CancelFormAlertDialog;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFormActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ControlPanelFragment.ControlPanelListener, MatchSettingsFragment.MatchSettingsListener,
        PowerCellsFragment.PowerCellsListener, EndGameFragment.EndGameListener, FinishFragment.FinishListener {
    private static final String TAG = "GameFormActivity";

    private Intent intent, i;
    BottomNavigationView bnvForm;
    Fragment selectedFragment;
    private Button btnBack;

    private ArrayList<Cycle> cycles;
    private FormInfo formInfo;
    public String teamNumber = "";
    int spnOptionSelectedIndex = 0, gameNumber = User.currentGame,
            cpRotation, cpPosition,
            endGameImageId = 0, finishImgId = 0, finishTicket = 0,
            finishDidCrash = 0, finishDefence;
    boolean leaveForm;
    CharSequence text;
    private Map<Integer, Fragment> fragsMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        if (leaveForm) {
            i = new Intent(GameFormActivity.this, MainActivity.class);
            finish();
            startActivity(i);
        }

        fragsMap = new HashMap<>();
        fragsMap.put(R.id.navPowerCells, new PowerCellsFragment());
        fragsMap.put(R.id.navControlPanel, new ControlPanelFragment());
        fragsMap.put(R.id.navEndGame, new EndGameFragment());
        fragsMap.put(R.id.navFinishForm, new FinishFragment());
        fragsMap.put(R.id.navMatchSettings, new MatchSettingsFragment());

        bnvForm = findViewById(R.id.bottomNavFormBar);

        intent = getIntent();
        if (intent.hasExtra(Keys.FORM_INFO)) {
            formInfo = intent.getParcelableExtra(Keys.FORM_INFO);

            teamNumber = formInfo.getTeamNumber();

            cpPosition = formInfo.getCpPosition();
            cpRotation = formInfo.getCpRotation();

            endGameImageId = formInfo.getEndGame();

            finishImgId = formInfo.getFinish();
            finishTicket = formInfo.getTicket();
            finishDidCrash = formInfo.getCrash();
            finishDefence = formInfo.getDefence();
            text = formInfo.getUserComment();

            selectedFragment = fragsMap.get(R.id.navFinishForm);
            bnvForm.getMenu().findItem(R.id.navFinishForm).setChecked(true);
        } else {
            selectedFragment = fragsMap.get(R.id.navMatchSettings);
            GeneralFunctions.resetForm();
        }

        if (intent.hasExtra(Keys.FINISH_PC)) {
            cycles = intent.getParcelableArrayListExtra(Keys.FINISH_PC);
        } else {
            cycles = new ArrayList<>();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForm, selectedFragment).commit();
        bnvForm.setOnNavigationItemSelectedListener(this);
    }

    public void openDialog() {
        CancelFormAlertDialog dialog = new CancelFormAlertDialog();
        dialog.show(getSupportFragmentManager(), "cancel form dialog");
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
    public void setDataMatchSettings(Intent msIntent) {
        teamNumber = msIntent.getStringExtra(Keys.MS_TEAM);
        gameNumber = msIntent.getIntExtra(Keys.MS_NUMBER, User.currentGame);
        spnOptionSelectedIndex = msIntent.getIntExtra(Keys.MS_TEAM_INDEX, 0);
    }

    @Override
    public ArrayList<Cycle> getCyclesFinish() {
        return cycles;
    }

    @Override
    public void setDataControlPanel(Intent cpIntent) {
        cpRotation = cpIntent.getIntExtra(Keys.CP_RC, 0);
        cpPosition = cpIntent.getIntExtra(Keys.CP_PC, 0);
    }

    @Override
    public void setDataPowerCells(Intent pcIntent) {
        ArrayList<Cycle> c = pcIntent.getParcelableArrayListExtra(Keys.PC_CYCLE);

        if (c != null && !c.isEmpty()) {
            cycles.addAll(c);
        }
    }

    @Override
    public void setDataEndGame(Intent egIntent) {
        endGameImageId = egIntent.getIntExtra(Keys.EG_IMG_ID, 0);
    }

    @Override
    public void setDataFinish(Intent finishIntent) {
        finishImgId = finishIntent.getIntExtra(Keys.FINISH_TEAM, 0);
        finishTicket = finishIntent.getIntExtra(Keys.FINISH_TICKET, 0);
        finishDidCrash = finishIntent.getIntExtra(Keys.FINISH_CRASH, 0);
        finishDefence = finishIntent.getIntExtra(Keys.FINISH_DEFENCE, 0);
        text = finishIntent.getCharSequenceExtra(Keys.FINISH_TEXT);
    }

    @Override
    public FormInfo getFormInfo() {
        formInfo = new FormInfo(teamNumber, gameNumber,
                cpRotation, cpPosition,
                endGameImageId,
                finishImgId, finishTicket, finishDidCrash, finishDefence, text);
        return formInfo;
    }
}
