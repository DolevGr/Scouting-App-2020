package com.example.primo2020v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.CyclesAdapter;
import com.example.primo2020v1.utils.Cycle;
import com.example.primo2020v1.utils.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchInfoActivity extends AppCompatActivity {
    private static final String TAG = "MatchInfoActivity";
    private TextView tvComment, tvSubmitted;
    private ImageView imgCrash, imgCard, imgDefence,
            imgCPRC, imgCPPC, imgEndGame, imgFinish;
    private ListView lvCycles;
    private Button btnBack;

    private ArrayList<Cycle> cycles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

        Intent intent = getIntent();
        Map<String, Object> info = (HashMap<String, Object>) intent.getSerializableExtra("Info");

        imgCrash = findViewById(R.id.imgDidCrash2);
        imgCard = findViewById(R.id.imgCard2);
        imgDefence = findViewById(R.id.imgDefence2);
        tvComment = findViewById(R.id.tvComment2);
        tvSubmitted = findViewById(R.id.tvSubmittedInfo);

        imgCPPC = findViewById(R.id.imgCPPCInfo);
        imgCPRC = findViewById(R.id.imgCPRCInfo);
        imgEndGame = findViewById(R.id.imgEndGameInfo);
        imgFinish = findViewById(R.id.imgFinishInfo);
        lvCycles = findViewById(R.id.lvCyclesInfo);
        btnBack = findViewById(R.id.btnBackInfo);

//        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                finish();
//            }
//        };

        tvSubmitted.setText("Submitted by: " + info.get("CommittedBy").toString());
        tvComment.setText(info.get("comment").toString());
        imgCard.setImageResource(User.finishTickets[Integer.parseInt(info.get("ticket").toString())]);
        imgCrash.setImageResource(User.finishCrash[Integer.parseInt(info.get("crash").toString())]);
        imgDefence.setImageResource(User.finishDefence[Integer.parseInt(info.get("defence").toString())]);

        imgCPRC.setImageResource(User.controlPanelRotation[Integer.parseInt(info.get("cpRotation").toString())]);
        imgCPPC.setImageResource(User.controlPanelPosition[Integer.parseInt(info.get("cpPosition").toString())]);
        imgEndGame.setImageResource(User.endGameImages[Integer.parseInt(info.get("endGame").toString())]);
        imgFinish.setImageResource(User.finishImages[Integer.parseInt(info.get("finish").toString())]);

        cycles = new ArrayList<>();
        int i = 0;
        boolean hasCycles;
        String stringCycle, phase, missed, lower, outer, inner, bullshit;
        do {
            stringCycle = "Cycle " + (i + 1);
            hasCycles = info.containsKey(stringCycle);
            if (hasCycles) {
//                cycles.add((Cycle) info.get(stringCycle));
                bullshit = info.get(stringCycle).toString();
                phase = getString(bullshit, "phase=", ',', 6);
                missed = getString(bullshit, "pcMissed=", ',', 9);
                lower = getString(bullshit, "pcLower=", '}', 8);
                outer = getString(bullshit, "pcOuter=", ',', 8);
                inner = getString(bullshit, "pcInner=", ',', 8);
                Log.d(TAG, "onCreate: " + bullshit + "\n" + missed + ", " + lower + ", " + outer + ", " + inner + ", " + phase);
                Cycle cycle = new Cycle(Integer.parseInt(missed), Integer.parseInt(lower),
                        Integer.parseInt(outer), Integer.parseInt(inner), Boolean.parseBoolean(phase));
                cycles.add(cycle);
            }
            i++;
        } while (hasCycles);

        CyclesAdapter cyclesAdapter = new CyclesAdapter(getApplicationContext(), R.layout.custom_cycles_adapter, cycles);
        lvCycles.setAdapter(cyclesAdapter);

        btnBack.setOnClickListener(v -> {
            finish();
            Intent i1 = new Intent(MatchInfoActivity.this, DrawerActivity.class);
            i1.putExtra("Navigation", R.id.navTeamOverview);
            startActivity(i1);
        });
    }

    private static String getString(String src, String begin, char end, int startFrom) {
        int index;
        String info = "";

        for (index = src.indexOf(begin) + startFrom; index < src.length(); index++) {
            if (src.charAt(index) == end)
                break;
            info += src.charAt(index);
        }

        return info;
    }
}
