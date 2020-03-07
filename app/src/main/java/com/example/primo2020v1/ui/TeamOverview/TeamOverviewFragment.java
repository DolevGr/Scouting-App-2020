package com.example.primo2020v1.ui.TeamOverview;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Adapters.GamesAdapter;
import com.example.primo2020v1.MatchInfoActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.Match;
import com.example.primo2020v1.utils.Pit;
import com.example.primo2020v1.utils.Statistics;
import com.example.primo2020v1.utils.User;
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
    private AutoCompleteTextView edTeamNumber;
    private TeamOverviewViewModel teamOverviewViewModel;
    private ListView lvTeamOverview;
    private TextView tvTeamName;

    private static boolean showMatches = false;
    private static String teamNumber, comments;
    private ArrayAdapter statsAdapter, pitAdapter, matchesAdapter;
    private DatabaseReference dbRef, dbRefStats, dbRefComments, dbRefPit, dbRefMatch;
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


        ArrayList<Integer> temp = new ArrayList<>(1);
        temp.add(1);

        valuesStats = new ArrayList<>();
        valuesPit = new ArrayList<>();
        valuesMatch = new ArrayList<>();
        dbRef = User.databaseReference.child(Keys.TEAMS);
        dbRefComments = User.databaseReference.child(Keys.COMMENTS);
        edTeamNumber.setText(teamNumber);

        if (showMatches) {
            showMatches();
            setListAction();
            showMatches = false;
        }

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_dropdown_item_1line,
                User.participants.keySet().toArray());
        edTeamNumber.setAdapter(adapter);
        edTeamNumber.setThreshold(1);

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
                    if (User.participants.containsKey(Integer.parseInt(number))) {
                        tvTeamName.setText(User.participants.get(Integer.parseInt(number)).replaceAll(" ", "\n"));
                    }
                }
            }
        });

        btnStats.setOnClickListener(v -> {
            btnStats.setClickable(false);
            lvTeamOverview.setOnItemClickListener((parent, view, position, id) -> {
            });

            teamNumber = edTeamNumber.getText().toString().trim();
            if (isValid()) {
                valuesStats.clear();
                statsAdapter = null;
                comments = "";
                dbRefStats = dbRef.child(teamNumber);

                dbRefComments.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(teamNumber)) {
                            dataSnapshot = dataSnapshot.child(teamNumber);

                            for (DataSnapshot d : dataSnapshot.getChildren()){
                                comments += "Match " + d.getKey() + ": " + d.getValue().toString() + '\n';
                            }
                        } else {
                            Toast.makeText(getContext(), "Comments were not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                dbRefStats.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Log.d(TAG, "onDataChange: " + dataSnapshot.hasChild(Keys.STATS));

                        if (dataSnapshot.hasChild(Keys.STATS)) {
                            DataSnapshot ds = dataSnapshot.child(Keys.STATS);
                            Statistics sumStats = null;

                            for (DataSnapshot d : ds.getChildren()) {
                                if (d.getKey().equals(Keys.STATS_SUM)) {
                                    sumStats = d.getValue(Statistics.class);
                                    break;
                                }
                            }

                            if (sumStats != null) {
                                valuesStats.add((double) sumStats.getDbTimesCrashed());
                                valuesStats.add((double) sumStats.getDbTimesDefended());
                                valuesStats.add((double) sumStats.getDbYellowCard());
                                valuesStats.add((double) sumStats.getDbRedCard());
                                valuesStats.add((double) sumStats.getTotalCycles() / sumStats.getNumberOfMatches());
                                valuesStats.add((double) sumStats.getTotalShots() / (sumStats.getTotalPCmissed() + sumStats.getTotalShots()));
                                valuesStats.add((double) sumStats.getTotalPClower() / sumStats.getNumberOfMatches());
                                valuesStats.add((double) sumStats.getTotalPCouter() / sumStats.getNumberOfMatches());
                                valuesStats.add((double) sumStats.getTotalPCinner() / sumStats.getNumberOfMatches());
                                valuesStats.add((double) sumStats.getDbCPRC());
                                valuesStats.add((double) sumStats.getDbCPPC());
                                valuesStats.add((double) sumStats.getDbClimb());
                                valuesStats.add((double) sumStats.getDbBalanced());

                                statsAdapter = new StatsAdapter(getContext(), R.layout.custom_teamoverview_stats,
                                        valuesStats, comments, temp);
                                lvTeamOverview.setAdapter(statsAdapter);
                            }

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

//            while (!hasStats && !hasComments){
//                if (hasComments && hasStats)
//                    break;
//            }


            btnStats.setClickable(true);
        });

        btnPit.setOnClickListener(v -> {
            v.setClickable(false);
            lvTeamOverview.setOnItemClickListener((parent, view, position, id) -> {
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
                            Pit pit = ds.getValue(Pit.class);

                            valuesPit.add(pit.getMass());
                            valuesPit.add(pit.getLanguage());
                            valuesPit.add(pit.getWheels());

                            String intake = pit.getIntake();
                            if (intake.equals("Both"))
                                intake = "Floor & Feeder";
                            valuesPit.add(intake);
                            valuesPit.add(pit.getCarry());

                            String shoot = pit.getShoot();
                            if (shoot.equals("Both"))
                                shoot = "Lowe & High";
                            valuesPit.add(shoot);
                            valuesPit.add(pit.getComment());
                            valuesPit.add(pit.isHasAuto() ? "Yes" : "No");
                            valuesPit.add(pit.isCanTrench() ? "Yes" : "No");
                            valuesPit.add(pit.isCanBumpers() ? "Yes" : "No");
                            valuesPit.add(Integer.toString(pit.getEndGame()));
                            valuesPit.add(Integer.toString(pit.getCprc()));
                            valuesPit.add(Integer.toString(pit.getCppc()));

                            pitAdapter = new PitAdapter(getContext(), R.layout.custom_teamoverview_pits, valuesPit, temp);
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
        });

        btnMatches.setOnClickListener(v -> {
            btnMatches.setClickable(false);
            teamNumber = edTeamNumber.getText().toString().trim();

            if (isValid()) {
                valuesMatch.clear();
                showMatches();
                setListAction();

                btnMatches.setClickable(true);
            } else {
                Toast.makeText(getContext(), "Invalid team number", Toast.LENGTH_SHORT).show();
            }
            btnMatches.setClickable(true);
        });

        return root;
    }

    private void setListAction() {
        lvTeamOverview.setOnItemClickListener((parent, view, position, id) -> {
            view.setClickable(false);
            final int match = valuesMatch.get(position).getGameNum();
            info = new HashMap<>();
            dbRefMatch = User.databaseReference.child(Keys.TEAMS).child(teamNumber);
            dbRefMatch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(Integer.toString(match))) {
                        dataSnapshot = dataSnapshot.child(Integer.toString(match));
                        Intent intent = new Intent(getContext(), MatchInfoActivity.class);

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            info.put(ds.getKey(), ds.getValue());
                        }
                        showMatches = true;
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
        });
    }

    private void showMatches() {
        for (Match match : User.matches) {
            if (match.hasTeam(teamNumber))
                valuesMatch.add(match);
        }

        matchesAdapter = new GamesAdapter(getContext(), R.layout.custom_games_layout, valuesMatch, teamNumber, false);
        lvTeamOverview.setAdapter(matchesAdapter);
    }

    private boolean isValid() {
        return !edTeamNumber.getText().toString().trim().equals("") &&
                User.participants.containsKey(Integer.parseInt(teamNumber));
    }
}