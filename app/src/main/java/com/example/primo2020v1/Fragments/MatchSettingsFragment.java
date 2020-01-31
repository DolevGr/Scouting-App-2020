package com.example.primo2020v1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;

import static android.content.ContentValues.TAG;

public class MatchSettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public interface MatchSettingsListener {
        void setDataMatchSettings(Intent msIntent);
    }


    private MatchSettingsListener listener;
    private Intent msIntent;
    private EditText edGameNumber, edTeamNumber;
    private Spinner spnTeam;
    private String[] positions;

    public static int gameNumber = User.currentGame, spnIndex = 0;
    public static String teamNumber = "";
    private ArrayAdapter<CharSequence> teamAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_settings, container, false);

        edGameNumber = v.findViewById(R.id.edGameNumber);
        edTeamNumber = v.findViewById(R.id.edTeamNumber);
        spnTeam = v.findViewById(R.id.spnTeam);

        positions = new String[]{"R Close", "R Middle", "R Far", "B Close", "B Middle", "B Far"};
        teamAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_item, positions);
        teamAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnTeam.setAdapter(teamAdapter);

        spnTeam.setOnItemSelectedListener(this);

        edGameNumber.setText(Integer.toString(gameNumber));
        edTeamNumber.setText(teamNumber);
        spnTeam.setSelection(spnIndex);

        edGameNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edGameNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    spnIndex = spnTeam.getSelectedItemPosition();
                    User.currentGame = gameNumber;
                }

                Log.d(TAG, "afterTextChanged: Game Number: " + gameNumber);
            }
        });

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                teamNumber = edTeamNumber.getText().toString();
            }
        });

        msIntent = new Intent(getContext(), GameFormActivity.class);

        return v;
    }


    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spnIndex = i;

        if (isValid())
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber), i);
        else
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(User.matches.size()-1), i);

        edGameNumber.setText(Integer.toString(gameNumber));
        edTeamNumber.setText(teamNumber);
        Log.d("Spinner Option ", "onClick: " + teamNumber);
    }

    private boolean isValid() {
        return gameNumber > 0 && gameNumber < User.matches.size();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


    //Fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (MatchSettingsListener) context;
        } catch (ClassCastException e){
            throw new RuntimeException(context.toString() +
                    " must implement MatchSettingsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        placeInfo();
        listener = null;
    }

    public void placeInfo(){
        teamNumber = edTeamNumber.getText().toString();
        gameNumber = Integer.parseInt(edGameNumber.getText().toString());

        msIntent.putExtra(Keys.MS_TEAM, teamNumber);
        msIntent.putExtra(Keys.MS_NUMBER, gameNumber);
        msIntent.putExtra(Keys.MS_TEAM_INDEX, spnIndex);
        listener.setDataMatchSettings(msIntent);
    }
}
