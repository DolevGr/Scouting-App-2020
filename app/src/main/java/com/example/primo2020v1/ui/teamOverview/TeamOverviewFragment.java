package com.example.primo2020v1.ui.teamOverview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Adapters.GamesAdapter;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamOverviewFragment extends Fragment {
    private static String teamNumber;
    private int avrgShots, avrgLower, avrgOuter, avrgInner, avrgRC, avrgPC, avrgBalanced, avrgClimb;
    private Button btnStats, btnPits, btnMatches;
    private EditText edTeamNumber;
    private TeamOverviewViewModel teamOverviewViewModel;
    private ListView lvTeamOverview;
    private ArrayAdapter statsAdapter, pitAdapter, matchesAdapter;
    private DatabaseReference dbRef, dbRefStats, dbRefPit, dbRefMatch;
    private ArrayList<Double> valuesStats;
    private ArrayList<String> valuesPit;
    private ArrayList<Match> valuesMatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        teamOverviewViewModel = ViewModelProviders.of(this).get(TeamOverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team_overview, container, false);

        btnStats = root.findViewById(R.id.btnStats);
        btnPits = root.findViewById(R.id.btnPits);
        btnMatches = root.findViewById(R.id.btnMatches);
        edTeamNumber = root.findViewById(R.id.edTeamNumber);
        lvTeamOverview = root.findViewById(R.id.lvTeamOverview);

        valuesStats = new ArrayList<>();
        valuesPit = new ArrayList<>();
        valuesMatch = new ArrayList<>();
        dbRef = User.databaseReference.child(Keys.TEAMS);
        edTeamNumber.setText(teamNumber);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
                    btnStats.setClickable(false);
                    valuesStats.clear();
                    statsAdapter = null;
                    dbRefStats = dbRef.child(teamNumber);

                    dbRefStats.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(Keys.STATS)) {
                                DataSnapshot ds = dataSnapshot.child(Keys.STATS);
                                int numMatches = (int) ds.getChildrenCount();

                                for (DataSnapshot d : ds.getChildren()) {
                                    avrgShots = Integer.parseInt(d.child("TotalShots").getValue().toString());
                                    avrgLower = Integer.parseInt(d.child("TotalLower").getValue().toString());
                                    avrgOuter = Integer.parseInt(d.child("TotalOuter").getValue().toString());
                                    avrgInner = Integer.parseInt(d.child("TotalInner").getValue().toString());
                                    avrgRC = Integer.parseInt(d.child("RC").getValue().toString());
                                    avrgPC = Integer.parseInt(d.child("PC").getValue().toString());
                                    avrgClimb = Integer.parseInt(d.child("Climb").getValue().toString());
                                    avrgBalanced = Integer.parseInt(d.child("Balanced").getValue().toString());
                                }

                                valuesStats.add((double) avrgShots / numMatches);
                                valuesStats.add((double) avrgLower / numMatches);
                                valuesStats.add((double) avrgOuter / numMatches);
                                valuesStats.add((double) avrgInner / numMatches);
                                valuesStats.add((double) avrgRC / numMatches);
                                valuesStats.add((double) avrgPC / numMatches);
                                valuesStats.add((double) avrgClimb / numMatches);
                                valuesStats.add((double) avrgBalanced / numMatches);

                                statsAdapter = new StatsAdapter(getContext(), R.layout.custom_teamoverview_stats, valuesStats);
                                lvTeamOverview.setAdapter(statsAdapter);
                            }
                            btnStats.setClickable(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            btnStats.setClickable(true);
                        }
                    });
                }
            }
        });

        btnPits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
                    btnPits.setClickable(false);
                    valuesPit.clear();
                    pitAdapter = null;
                    dbRefPit = dbRef.child(teamNumber);

                    dbRefPit.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(Keys.PIT)) {
                                DataSnapshot ds = dataSnapshot.child(Keys.PIT);

                                valuesPit.add(ds.child("RobotHeight").getValue().toString());
                                valuesPit.add(ds.child("RobotMass").getValue().toString());
                                valuesPit.add(ds.child("RobotChassie").getValue().toString());
                                valuesPit.add(ds.child("Comment").getValue().toString());
                                valuesPit.add(ds.child("HasAuto").getValue().toString());
                                valuesPit.add(ds.child("EndGame").getValue().toString());
                                valuesPit.add(ds.child("CPRC").getValue().toString());
                                valuesPit.add(ds.child("CPPC").getValue().toString());

                                pitAdapter = new PitAdapter(getContext(), R.layout.custom_teamoverview_pits, valuesPit);
                                lvTeamOverview.setAdapter(pitAdapter);
                            }
                            btnPits.setClickable(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            btnPits.setClickable(true);
                        }
                    });
                }
            }
        });

        btnMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
                    btnMatches.setClickable(false);
                    valuesMatch.clear();
                    matchesAdapter = null;
                    dbRefMatch = User.databaseReference.child("Matches");

                    dbRefMatch.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsMatches : dataSnapshot.getChildren()) {
                                for (DataSnapshot dsTeam : dsMatches.getChildren()) {
                                    if (dsTeam.getValue().toString().equals(teamNumber)) {
                                        Match m = dsMatches.getValue(Match.class);
                                        m.setGameNum(Integer.parseInt(dsMatches.getKey()));
                                        valuesMatch.add(m);
                                    }
                                }
                            }
                            matchesAdapter = new GamesAdapter(getContext(), R.layout.custom_games_layout, valuesMatch);
                            lvTeamOverview.setAdapter(matchesAdapter);
                            btnMatches.setClickable(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            btnMatches.setClickable(true);
                        }
                    });
                }
            }
        });

        return root;
    }

    private boolean isValid() {
        return !edTeamNumber.getText().toString().trim().equals("") &&
                User.teams.containsKey(Integer.parseInt(teamNumber));
    }
}