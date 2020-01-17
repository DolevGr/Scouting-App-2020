package com.example.primo2020v1.Fragments;

import android.content.Context;
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

import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.User;

public class MatchSettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public interface MatchSettingsListener {
        void getDataMatchSettings(String teamSelected, int position, int gameNumber);
    }


    private MatchSettingsListener listener;
    private EditText edGameNumber, edTeamNumber;
    private Spinner spnTeam;
    private String[] positions;

    public static int gameNumber = User.currentGame, spnIndex;
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

        edGameNumber.addTextChangedListener(textWatcherGameNumber);
        edTeamNumber.addTextChangedListener(textWatcherTeamNumber);

        return v;
    }

    private TextWatcher textWatcherTeamNumber = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            teamNumber = edTeamNumber.getText().toString();
        }
    };

    private TextWatcher textWatcherGameNumber = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            if(edGameNumber.getText().toString().trim().equals(""))
                gameNumber = 0;
            else {
                gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                spnIndex = spnTeam.getSelectedItemPosition();
            }
        }
    };


    //Spinner overrides
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spnIndex = i;
        teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber), i);

        edGameNumber.setText(Integer.toString(gameNumber));
        edTeamNumber.setText(teamNumber);
        Log.d("Spinner Option ", "onClick: " + teamNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


    //Fragment overrides
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

        teamNumber = edTeamNumber.getText().toString();
        gameNumber = Integer.parseInt(edGameNumber.getText().toString());
        listener.getDataMatchSettings(teamNumber, spnIndex, gameNumber);
        listener = null;
    }

    public void resetFragment(){
        gameNumber = 0;
        spnIndex = 0;
        teamNumber = "";
    }
}
