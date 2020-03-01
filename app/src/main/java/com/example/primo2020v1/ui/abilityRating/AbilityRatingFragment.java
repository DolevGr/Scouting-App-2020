package com.example.primo2020v1.ui.abilityRating;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.Statistics;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AbilityRatingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private AbilityRatingViewModel slideshowViewModel;
    private Spinner spnCategory;
    private Button btnSearch;
    private ListView lvAbilityRating;

    private Map<Integer, Statistics> allStatistics; //TeamNumber; SumStats for the team
    private Map<Integer, Double> teamsRating; //TeamNumber; Their rating

    private AbilityRatingAdapter adapter = null;
    private DatabaseReference dbRefSumStats;
    private String category;
    private String[] categories = {"totalPClower", "totalPCouter", "totalPCinner", "dbCPRC", "dbCPPC", "dbClimb", "dbBalanced"};

    private boolean empty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel = ViewModelProviders.of(this).get(AbilityRatingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ability_rating, container, false);
        spnCategory = root.findViewById(R.id.spnCategoryAbility);
        btnSearch = root.findViewById(R.id.btnSearchAbility);
        lvAbilityRating = root.findViewById(R.id.lvAbilityRating);

        dbRefSumStats = User.databaseReference.child(Keys.TEAMS);
        final ArrayAdapter teamAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ability_categories, R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(teamAdapter);
        allStatistics = new HashMap<>();
        teamsRating = new HashMap<>();

        spnCategory.setOnItemSelectedListener(this);
        setTeamRanking();

        btnSearch.setOnClickListener((v) -> setListViewAdapter());

        return root;
    }

    private void setListViewAdapter() {
        double rating;
        teamsRating.clear();

        for (Integer team : allStatistics.keySet()) {
            Statistics statistics = allStatistics.get(team);
            if (category.equals(categories[0]) || category.equals(categories[1]) || category.equals(categories[2]))
                rating = statistics.getCategory(category) / statistics.getNumberOfMatches();
            else
                rating = statistics.getCategory(category);
            teamsRating.put(team, rating);
        }
        List<Map.Entry<Integer, Double>> teamsRatingLst = new ArrayList<>(teamsRating.entrySet());
        Collections.sort(teamsRatingLst, (t1, t2) -> t2.getValue().compareTo(t1.getValue()));

        adapter = new AbilityRatingAdapter(getContext(), R.layout.custom_ability_rating, teamsRatingLst);
        lvAbilityRating.setAdapter(adapter);
    }

    private void setTeamRanking() {
        empty = true;
        if (allStatistics.isEmpty()) {
            empty = false;
            dbRefSumStats.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot team : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: " + team.getKey());
                        int teamNumber = Integer.parseInt(team.getKey());
                        setAllStatistics(team, teamNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            empty = false;
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (empty)
                Toast.makeText(getContext(), "Stats were not found", Toast.LENGTH_SHORT).show();
        }, 10000);

    }

    private void setAllStatistics(DataSnapshot team, int teamNumber) {
        team = team.child(Keys.STATS);
        for (DataSnapshot ds : team.getChildren()) {
            if (ds.getKey().equals(Keys.STATS_SUM)) {
                Statistics statistics = ds.getValue(Statistics.class);
                allStatistics.put(teamNumber, statistics);
            }

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = categories[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}