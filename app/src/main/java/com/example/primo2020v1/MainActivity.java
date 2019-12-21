package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.Match;
import com.example.primo2020v1.libs.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView lvGames;
    Intent intent, next;
    String username;
    Context context;
    Button btnNewForm, btnEditForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        intent = getIntent();
        username = intent.getStringExtra("Username");
        lvGames = (ListView) findViewById(R.id.lvGames);
        btnNewForm = (Button) findViewById(R.id.btnNewForm);
        btnEditForm = (Button) findViewById(R.id.btnEditForm);

//        User.teamsHM.put(4586, "Primo");
//        User.teamsHM.put(1574, "Miscar");
//        User.teamsHM.put(1580, "The Blue Monkeys");
//        User.teamsHM.put(1576, "Voltrix");
//        User.teamsHM.put(1657, "Hamosad");
//        User.teamsHM.put(1937, "Elysium");
//        User.teamsHM.put(1942, "Cinderella");
//        User.teamsHM.put(1943, "Neat Team");

        User.matches = new ArrayList<>();

        //**********************************************
        for(int i = 1; i < User.NUMBER_OF_MATCHES; i++) {
            User.matches.add(new Match("R F "+i, "R C "+i, "R B "+i, "B F "+i, "B C "+i, "B B "+i, i));
            //User.teamsT[i] = new Team("R1 "+i, "R2 "+i, "R3 "+i, "B1 "+i, "B2 "+i, "B3 "+i, i);
        }
        //***************************************** BAD CODE NEEDS FIXING

        GamesAdapter gamesAdapter = new GamesAdapter(context, R.layout.costum_games_layout, User.matches);
        lvGames.setAdapter(gamesAdapter);

        btnNewForm.setOnClickListener(this);
        btnEditForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewForm:
                next = new Intent(MainActivity.this, GameFormActivity.class);
                break;
            case R.id.btnEditForm:
                next = new Intent(MainActivity.this, EditFormActivity.class);
            default:
                next = null;
                break;
        }

        if(next != null) {
            startActivity(next);
        }
    }
}
