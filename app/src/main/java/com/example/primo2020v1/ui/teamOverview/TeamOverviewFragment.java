package com.example.primo2020v1.ui.teamOverview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.primo2020v1.R;

import java.util.HashMap;
import java.util.Map;

public class TeamOverviewFragment extends Fragment {
    private Button btnStats, btnPits, btnMatches;
    private EditText edTeamNumber;
    private TeamOverviewViewModel galleryViewModel;
    private Fragment selectedFragment;
    private Map<Integer, Fragment> fragMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(TeamOverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team_overview, container, false);
        btnStats = root.findViewById(R.id.btnStats);
        btnPits = root.findViewById(R.id.btnPits);
        btnMatches = root.findViewById(R.id.btnMatches);
        edTeamNumber = root.findViewById(R.id.edTeamNumber);

        fragMap = new HashMap<>();
        fragMap.put(R.id.fragmentStats, new StatsFragment());

        selectedFragment = fragMap.get(R.id.fragmentStats);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTeamOverview, selectedFragment).commit();
//
//        btnStats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedFragment = fragMap.get(R.id.fragmentStats);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentTeamOverview, selectedFragment).commit();
//            }
//        });

        return root;
    }
}