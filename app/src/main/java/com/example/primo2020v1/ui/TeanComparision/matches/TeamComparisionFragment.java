package com.example.primo2020v1.ui.TeanComparision.matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.Adapters.GamesAdapter;
import com.example.primo2020v1.R;

public class TeamComparisionFragment extends Fragment {
    private ListView lvGames;
    private Intent next;
    private Button btnNewForm, btnEditForm, btnExit;
    private GamesAdapter gamesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team_comparision, container, false);



        return v;
    }
}