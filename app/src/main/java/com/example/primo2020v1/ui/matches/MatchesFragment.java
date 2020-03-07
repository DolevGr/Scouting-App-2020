package com.example.primo2020v1.ui.Matches;

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
import com.example.primo2020v1.EditFormActivity;
import com.example.primo2020v1.GameFormActivity;
import com.example.primo2020v1.LoginActivity;
import com.example.primo2020v1.PitsFormActivity;
import com.example.primo2020v1.R;
import com.example.primo2020v1.utils.GeneralFunctions;
import com.example.primo2020v1.utils.User;

import java.util.Map;

public class MatchesFragment extends Fragment implements View.OnClickListener {
    private ListView lvGames;
    private Intent next;
    private Button btnNewForm, btnEditForm, btnExit, btnPitsForm;
    private GamesAdapter gamesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        lvGames = v.findViewById(R.id.lvGames);
        btnNewForm = v.findViewById(R.id.btnNewForm);
        btnEditForm = v.findViewById(R.id.btnEditForm);
        btnExit = v.findViewById(R.id.btnExit);
        btnPitsForm = v.findViewById(R.id.btnPits);

        if (lvGames.getAdapter() == null && gamesAdapter == null) {
            gamesAdapter = new GamesAdapter(getContext(), R.layout.custom_games_layout, User.matches, "4586", true);
            lvGames.setAdapter(gamesAdapter);
        }

        btnNewForm.setOnClickListener(this);
        btnEditForm.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnPitsForm.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewForm:
                User.formMatch = User.liveMatch;
                next = new Intent(getContext(), GameFormActivity.class);
                break;

            case R.id.btnEditForm:
                next = new Intent(getContext(), EditFormActivity.class);
                break;

            case R.id.btnPits:
                next = new Intent(getContext(), PitsFormActivity.class);
                break;

            case R.id.btnExit:
                Map<String, String> map = GeneralFunctions.readFromFile("Info.txt", getContext());

                map.remove("Name");
                map.remove("Password");
                map.remove("Rank");
                GeneralFunctions.writeToFile("Info.txt", getContext(), map);

                next = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                break;

            default:
                next = null;
                break;
        }

        if (next != null) {
            startActivity(next);
        }
    }
}