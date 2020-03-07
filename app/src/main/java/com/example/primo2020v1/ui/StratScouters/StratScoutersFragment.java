package com.example.primo2020v1.ui.StratScouters;

import android.os.Bundle;
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
import com.example.primo2020v1.utils.User;

import java.util.ArrayList;

public class StratScoutersFragment extends Fragment {
    private StratScoutersModel stratScoutersModel;
    private Button btnNewComment, btnViewComments;
    private EditText edMatchNumber;
    private AutoCompleteTextView edTeamNumber;
    private ListView lvStrats;


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

        btnNewComment.setOnClickListener(v -> {
            int matchNumber = Integer.parseInt(edMatchNumber.getText().toString().trim()),
                    teamNumber = Integer.parseInt(edTeamNumber.getText().toString().trim());

            if ((matchNumber > 0 && matchNumber < User.matches.size()) && User.participants.containsKey(teamNumber)) {
                ArrayList<String> single = new ArrayList<>();
                single.add("1");
                StratsScouterAdapter adapter = new StratsScouterAdapter(getContext(), R.layout.custom_strats_comment, single,
                        matchNumber, teamNumber, true);
                lvStrats.setAdapter(adapter);
            }
        });

        btnViewComments.setOnClickListener(v -> {
            int matchNumber = Integer.parseInt(edMatchNumber.getText().toString().trim()),
                    teamNumber = Integer.parseInt(edTeamNumber.getText().toString().trim());

            if ((matchNumber > 0 && matchNumber < User.matches.size()) && User.participants.containsKey(teamNumber)) {
                ArrayList<String> single = new ArrayList<>();
                single.add("1");
                StratsScouterAdapter adapter = new StratsScouterAdapter(getContext(), R.layout.custom_strats_comment, single,
                        matchNumber, teamNumber, false);
                lvStrats.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Match or Team is invalid", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
