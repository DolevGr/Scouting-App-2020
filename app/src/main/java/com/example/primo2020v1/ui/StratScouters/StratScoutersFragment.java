package com.example.primo2020v1.ui.StratScouters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StratScoutersFragment extends Fragment {
    private StratScoutersModel stratScoutersModel;
    private DatabaseReference dbRef;

    private Button btnNewComment, btnViewComments;
    private EditText edMatchNumber;
    private AutoCompleteTextView edTeamNumber;
    private ListView lvStrats;

    private StratScouterAdapter adapter;
    private ArrayList<String> single, commentsFromDB;
    private int teamNumber, matchNumber;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stratScoutersModel = ViewModelProviders.of(this).get(StratScoutersModel.class);
        View root = inflater.inflate(R.layout.fragment_strat_scouter, container, false);

        lvStrats = root.findViewById(R.id.lvStarts);
        btnNewComment = root.findViewById(R.id.btnNewComment);
        btnViewComments = root.findViewById(R.id.btnViewComment);
        edMatchNumber = root.findViewById(R.id.edMatchNumberStrats);
        edTeamNumber = root.findViewById(R.id.edTeamNumberStrats);

        ArrayAdapter<Object> adapterTeams;
        adapterTeams = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, User.participants.keySet().toArray());
        edTeamNumber.setAdapter(adapterTeams);
        edTeamNumber.setThreshold(1);
        dbRef = User.databaseReference.child(Keys.SCOUTER_COMMENTS);

        commentsFromDB = new ArrayList<>();
        single = new ArrayList<>();
        single.add("1");

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edTeamNumber.getText().toString().trim().equals(""))
                    teamNumber = Integer.parseInt(edTeamNumber.getText().toString().trim());
            }
        });

        edMatchNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edMatchNumber.getText().toString().trim().equals(""))
                    matchNumber = Integer.parseInt(edMatchNumber.getText().toString().trim());
            }
        });

        btnNewComment.setOnClickListener(v -> {
            if (isValid()) {
                adapter = new StratScouterAdapter(getContext(), R.layout.custom_strats_comment,
                        single, matchNumber, teamNumber);
                lvStrats.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Match or Team is invalid", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewComments.setOnClickListener(v -> {
            if (isValidTeam()) {
                this.commentsFromDB.clear();
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Integer.toString(teamNumber))) {
                            DataSnapshot dsTeamNumber = dataSnapshot.child(Integer.toString(teamNumber));
                            for (DataSnapshot ds : dsTeamNumber.getChildren()) {
                                commentsFromDB.add(ds.getValue().toString().trim());
                            }

                            if (adapter != null) {
                                lvStrats.setAdapter(null);
                            }
                            adapter = new StratScouterAdapter(getContext(), R.layout.custom_strats_comment,
                                    commentsFromDB, teamNumber);
                            lvStrats.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            } else {
                Toast.makeText(getContext(), "Match or Team is invalid", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private boolean isValid() {
        return isValidMatch() && isValidTeam();
    }

    private boolean isValidMatch() {
        return matchNumber > 0 && matchNumber < User.matches.size();
    }

    private boolean isValidTeam() {
        return User.participants.containsKey(teamNumber);
    }
}
