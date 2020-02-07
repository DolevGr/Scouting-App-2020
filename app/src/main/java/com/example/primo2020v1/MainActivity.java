package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.Adapters.GamesAdapter;
import com.example.primo2020v1.libs.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView lvGames;
    private Intent intent, next;
    private Context context;
    private Button btnNewForm, btnEditForm, btnExit;
    private GamesAdapter gamesAdapter;
    private Toolbar toolbar;

    private String username;
    private boolean priv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvGames = (ListView) findViewById(R.id.lvGames);
        btnNewForm = (Button) findViewById(R.id.btnNewForm);
        btnEditForm = (Button) findViewById(R.id.btnEditForm);
        btnExit = (Button) findViewById(R.id.btnExit);
        //toolbar = findViewById(R.id.toolbar);

        context = getApplicationContext();
        intent = getIntent();
        username = intent.getStringExtra("Username");
        if (username != null && !username.equals(""))
            User.username = username;
        priv = intent.getBooleanExtra("Privilege", false);

        gamesAdapter = new GamesAdapter(context, R.layout.costum_games_layout, User.matches);
        lvGames.setAdapter(gamesAdapter);

        btnNewForm.setOnClickListener(this);
        btnEditForm.setOnClickListener(this);
        btnExit.setOnClickListener(this);
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

            case R.id.btnExit:
                next = new Intent(MainActivity.this, LoginActivity.class);
                finish();
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
