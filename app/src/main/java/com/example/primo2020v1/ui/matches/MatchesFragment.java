package com.example.primo2020v1.ui.matches;

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
import com.example.primo2020v1.libs.User;

public class MatchesFragment extends Fragment implements View.OnClickListener {
    private ListView lvGames;
    private Intent next;
    private Button btnNewForm, btnEditForm, btnExit, btnPitsForm;
    private GamesAdapter gamesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        lvGames = (ListView) v.findViewById(R.id.lvGames);
        btnNewForm = (Button) v.findViewById(R.id.btnNewForm);
        btnEditForm = (Button) v.findViewById(R.id.btnEditForm);
        btnExit = (Button) v.findViewById(R.id.btnExit);
        btnPitsForm = v.findViewById(R.id.btnPits);

        if (gamesAdapter == null)
            gamesAdapter = new GamesAdapter(getContext(), R.layout.costum_games_layout, User.matches);
        lvGames.setAdapter(gamesAdapter);

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
                next = new Intent(getContext(), GameFormActivity.class);
                break;

            case R.id.btnEditForm:
                next = new Intent(getContext(), EditFormActivity.class);
                break;

            case R.id.btnPits:
                next = new Intent(getContext(), PitsFormActivity.class);
                break;

            case R.id.btnExit:
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