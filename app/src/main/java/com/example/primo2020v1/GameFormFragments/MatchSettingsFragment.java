package com.example.primo2020v1.GameFormFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.libs.GeneralFunctions;
import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;

public class MatchSettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public interface MatchSettingsListener {
        void setDataMatchSettings(Intent msIntent);
    }


    private MatchSettingsListener listener;
    private Intent msIntent;
    private EditText edGameNumber, edTeamNumber;
    private ImageView imgbtnSwitchFields;
    private Spinner spnTeam;
    private String[] positions;
    //private int[] locks = {R.drawable.ic_locked_foreground, R.drawable.ic_unlocked_foreground};

    public static int gameNumber, spnIndex;
    public static String teamNumber;
    public boolean swichFields;
    private ArrayAdapter<CharSequence> teamAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_settings, container, false);

        edGameNumber = v.findViewById(R.id.edGameNumber);
        edTeamNumber = v.findViewById(R.id.edTeamNumber);
        spnTeam = v.findViewById(R.id.spnTeam);
        imgbtnSwitchFields = v.findViewById(R.id.imgbtnSwitchFields);
        swichFields = true;

        positions = new String[]{"R Close", "R Middle", "R Far", "B Close", "B Middle", "B Far"};
        teamAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_item, positions);
        teamAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnTeam.setAdapter(teamAdapter);

        spnTeam.setOnItemSelectedListener(this);

        switchFields();
        edGameNumber.setText(Integer.toString(gameNumber));
        edTeamNumber.setText(teamNumber);
        spnTeam.setSelection(spnIndex == -1 ? 0 : spnIndex);


        imgbtnSwitchFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFields();
            }
        });

        edGameNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edGameNumber.getText().toString().trim().equals("")) {
                    gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());
                    spnIndex = spnTeam.getSelectedItemPosition();
                    User.currentGame = gameNumber;
                }
            }
        });

        edTeamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edTeamNumber.getText().toString().trim().equals("")) {
                    teamNumber = edTeamNumber.getText().toString().trim();
                }
            }
        });

        msIntent = new Intent(getContext(), GameFormActivity.class);
        return v;
    }


    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (isValid() && spnIndex != i) {
            teamNumber = GeneralFunctions.convertTeamFromSpinnerTODB(User.matches.get(gameNumber - 1), i);
            spnIndex = i;
        }

        edGameNumber.setText(Integer.toString(gameNumber));
        edTeamNumber.setText(teamNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private boolean isValid() {
        return gameNumber > 0 && gameNumber < User.matches.size();
    }

    private void switchFields() {
        //imgbtnSwitchFields.setImageDrawable(getResources().getDrawable(swichFields ? locks[0] : locks[1]));
        swichFields = !swichFields;
        edGameNumber.setEnabled(swichFields);
        edTeamNumber.setEnabled(swichFields);
    }


    //Fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MatchSettingsListener) context;
        } catch (ClassCastException e) {
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

    public void placeInfo() {
        teamNumber = edTeamNumber.getText().toString().trim();
        gameNumber = Integer.parseInt(edGameNumber.getText().toString().trim());

        msIntent.putExtra(Keys.MS_TEAM, teamNumber);
        msIntent.putExtra(Keys.MS_NUMBER, gameNumber);
        msIntent.putExtra(Keys.MS_TEAM_INDEX, spnIndex);
        listener.setDataMatchSettings(msIntent);
    }
}
