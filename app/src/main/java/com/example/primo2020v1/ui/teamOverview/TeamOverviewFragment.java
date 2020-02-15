package com.example.primo2020v1.ui.teamOverview;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Adapters.GamesAdapter;
import com.example.primo2020v1.MatchInfoActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamOverviewFragment extends Fragment {
    private Button btnStats, btnPit, btnMatches;
    private EditText edTeamNumber;
    private TeamOverviewViewModel teamOverviewViewModel;
    private ListView lvTeamOverview;
    private TextView tvTeamName;

    private static String teamNumber;
    private int avrgShots, avrgLower, avrgOuter, avrgInner, avrgRC, avrgPC, avrgBalanced, avrgClimb;
    private ArrayAdapter statsAdapter, pitAdapter, matchesAdapter;
    private DatabaseReference dbRef, dbRefStats, dbRefPit, dbRefMatch;
    private ArrayList<Double> valuesStats;
    private ArrayList<String> valuesPit;
    private ArrayList<Match> valuesMatch;
    private Map<String, Object> info;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        teamOverviewViewModel = ViewModelProviders.of(this).get(TeamOverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team_overview, container, false);

        btnStats = root.findViewById(R.id.btnStats);
        btnPit = root.findViewById(R.id.btnPits);
        btnMatches = root.findViewById(R.id.btnMatches);
        edTeamNumber = root.findViewById(R.id.edTeamNumber);
        lvTeamOverview = root.findViewById(R.id.lvTeamOverview);
        tvTeamName = root.findViewById(R.id.tvTeamName);

        valuesStats = new ArrayList<>();
        valuesPit = new ArrayList<>();
        valuesMatch = new ArrayList<>();
        dbRef = User.databaseReference.child(Keys.TEAMS);
        edTeamNumber.setText(teamNumber);

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTeamName.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edTeamNumber.getText().toString().trim().equals("")) {
                    String number = edTeamNumber.getText().toString().trim();
                    if (User.teams.containsKey(Integer.parseInt(number))) {
                        tvTeamName.setText(User.teams.get(Integer.parseInt(number)));
                    }
                }
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStats.setClickable(false);
                lvTeamOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
                });

                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
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
                            } else {
                                Toast.makeText(getContext(), "Stats were not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            btnStats.setClickable(true);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Invalid team number", Toast.LENGTH_SHORT).show();
                }
                btnStats.setClickable(true);
            }
        });

        btnPit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPit.setClickable(false);
                lvTeamOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
                });

                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
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
                                btnPit.setClickable(true);
                            } else {
                                Toast.makeText(getContext(), "Pit was not found", Toast.LENGTH_SHORT).show();
                            }
                            btnPit.setClickable(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            btnPit.setClickable(true);
                        }
                    });
                } else {
                    btnPit.setClickable(true);
                    Toast.makeText(getContext(), "Invalid team number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMatches.setClickable(false);
                teamNumber = edTeamNumber.getText().toString().trim();
                if (isValid()) {
                    if (matchesAdapter != null)
                        matchesAdapter.clear();

                    for (Match match : User.matches) {
                        if (match.hasTeam(teamNumber))
                            valuesMatch.add(match);
                    }

                    matchesAdapter = new GamesAdapter(getContext(), R.layout.custom_games_layout, valuesMatch, teamNumber);
                    lvTeamOverview.setAdapter(matchesAdapter);
                    btnMatches.setClickable(true);

                    lvTeamOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
                            view.setClickable(false);
                            final int match = valuesMatch.get(position).getGameNum();
                            info = new HashMap<>();
                            dbRefMatch = User.databaseReference.child("Teams").child(teamNumber);
                            dbRefMatch.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(Integer.toString(match))) {
                                        dataSnapshot = dataSnapshot.child(Integer.toString(match));
                                        Intent intent =  new Intent(getContext(), MatchInfoActivity.class);

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            info.put(ds.getKey(), ds.getValue());
                                        }
                                        intent.putExtra("Info", (Serializable) info);
                                        startActivity(intent);
                                        view.setClickable(true);
                                    } else {
                                        view.setClickable(true);
                                        Toast.makeText(getContext(), "Match was not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    view.setClickable(true);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Invalid team number", Toast.LENGTH_SHORT).show();
                }
                btnMatches.setClickable(true);
            }
        });

        return root;
    }

    private boolean isValid() {
        return !edTeamNumber.getText().toString().trim().equals("") &&
                User.teams.containsKey(Integer.parseInt(teamNumber));
    }
}