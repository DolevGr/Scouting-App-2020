package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primo2020v1.utils.GeneralFunctions;
import com.example.primo2020v1.utils.Keys;
import com.example.primo2020v1.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText edName, edPass;
    Button btnLogin;
    Intent in;
    Context context;

    String name, password, nameFromDB, passFromDB, rankFromDB = "", rank, dateFromDB;
    boolean debug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        context = getApplicationContext();

        btnLogin.setOnClickListener(view -> {
            name = edName.getText().toString().trim();
            password = edPass.getText().toString().trim();
            rank = "Scouter";


            if (!password.equals("")) {
                DatabaseReference dbRef = User.databaseReference.child(Keys.USERS);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataSnapshot userDataSnapshot = null;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.child("name").getValue().toString().equals(name)) {
                                userDataSnapshot = ds;
                                break;
                            }
                        }

                        if (userDataSnapshot == null)
                            return;

                        try {
                            nameFromDB = userDataSnapshot.child("name").getValue().toString().trim();
                            passFromDB = userDataSnapshot.child("password").getValue().toString().trim();
                            rankFromDB = userDataSnapshot.child("rank").getValue().toString().trim();
                            rank = rankFromDB;
                            Log.d(TAG, "onDataChange: " + rank + ", " + name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (isValidName() && isValidPassword()) {

                            Map<String, String> map = GeneralFunctions.readFromFile("Info.txt", getApplicationContext());
                            map.put("Name", name);
                            map.put("Password", password);
                            map.put("Rank", rank);

                            GeneralFunctions.writeToFile("Info.txt", getApplicationContext(), map);

                            in = new Intent(LoginActivity.this, DrawerActivity.class);
                            in.putExtra("Username", edName.getText().toString());
                            in.putExtra("Rank", rank);
                            startActivity(in);

                        } else if (!isValidPassword()) {
                            Toast.makeText(context.getApplicationContext(), "Password is incorrect", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            } else {
                enterOnDebug();
                checkPassAndUsername();
            }
        });

//        Map<String, String> map = GeneralFunctions.readFromFile("Info.txt", getApplicationContext());
//
//        if (map != null) {
//            name = map.get("Name");
//            password = map.get("Password");
//            rank = map.get("Rank");
//            String date = map.get("Date");
//
//            if (name != null && password != null && rank != null) {
//                edName.setText(name);
//                edPass.setText(password);
//                edName.setEnabled(false);
//                edPass.setEnabled(false);
//                btnLogin.setEnabled(false);
//
//                in = new Intent(LoginActivity.this, DrawerActivity.class);
//                in.putExtra("Username", name);
//                in.putExtra("Rank", rank);
//                startActivity(in);
//                finish();
//            }
//        }
    }

    private void checkPassAndUsername() {
        if (edName.getText().toString().equals("") || edPass.getText().toString().equals("")) {
            Toast.makeText(context.getApplicationContext(), "Enter Username or Password", Toast.LENGTH_LONG).show();
            Log.d(TAG, "checkPassAndUsername: " + passFromDB);
        } else if (!isValidName() || !isValidPassword()) {
            Toast.makeText(context.getApplicationContext(), "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidName() {
        return name.equals(nameFromDB);
    }

    private boolean isValidPassword() {
        return password.toLowerCase().equals(passFromDB.toLowerCase());
    }

    private void enterOnDebug() {
        if (debug) {
            in = new Intent(LoginActivity.this, DrawerActivity.class);

            in.putExtra("Username", edName.getText().toString());
            in.putExtra("Rank", rank);
            startActivity(in);

        }
    }
}
