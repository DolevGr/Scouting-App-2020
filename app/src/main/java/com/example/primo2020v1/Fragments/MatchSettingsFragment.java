package com.example.primo2020v1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.R;

public class MatchSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_before_match, container, false);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        GameFormActivity g = new GameFormActivity();
//
//        g.edGameNumber = getActivity().findViewById(R.id.edGameNumber);
//        g.edTeamNumber = getActivity().findViewById(R.id.edTeamNumber);
//        g.teamSpinner = getActivity().findViewById(R.id.spnTeam);
//
//        g.edTeamNumber.setText(g.teamNumber);
//        g.edGameNumber.setText(g.gameNumber);
//    }
}
