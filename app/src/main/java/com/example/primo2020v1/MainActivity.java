package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.libs.User;

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
                break;
            default:
                next = null;
                break;
        }

        if(next != null) {
            startActivity(next);
        }
    }
}
