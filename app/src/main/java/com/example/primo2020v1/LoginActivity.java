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

import com.example.primo2020v1.libs.Keys;
import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText edName, edPass;
    Button btnLogin;
    Intent in;
    Context context;

    String name, password, nameFromDB, passFromDB, rankFromDB = "", rank;
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

            if (User.members.contains(name) && !password.equals("")) {
                DatabaseReference dbRef = User.databaseReference.child(Keys.USERS).child(Integer.toString(User.members.indexOf(name)));
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            nameFromDB = dataSnapshot.child("name").getValue().toString().trim();
                            passFromDB = dataSnapshot.child("password").getValue().toString().trim();
                            rankFromDB = dataSnapshot.child("rank").getValue().toString().trim();
                            rank = rank.equals(rankFromDB) ? "Scouter" : rankFromDB;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (isValidName() && isValidPassword()) {
                            try {
//                                    String path = Environment.getDataDirectory().getPath();
//                                    FileWriter loginFileWriter = new FileWriter(path + "/LoginInfo.txt");
//                                    loginFileWriter.write(name + '\n' + password + '\n' + rank + '\n');
//                                    loginFileWriter.close();

                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("LoginInfo.txt", Context.MODE_PRIVATE));
                                outputStreamWriter.write(name + '\n' + password + '\n' + rank + '\n');
                                outputStreamWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

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

//        String path = Environment.getDataDirectory().getPath();
        try {
//            BufferedReader loginFileReader = new BufferedReader(new FileReader(path + "/LoginInfo.txt"));
//            String name = loginFileReader.readLine();
//            String pass = loginFileReader.readLine();
//            String privString = loginFileReader.readLine();
//            loginFileReader.close();
//            edName.setText(name);
//            edPass.setText(pass);
            InputStream inputStream = context.openFileInput("LoginInfo.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                name = bufferedReader.readLine();
                password = bufferedReader.readLine();
                rank = bufferedReader.readLine();
                inputStream.close();

                if (name != null && password != null && rank != null) {
                    edName.setText(name);
                    edPass.setText(password);
                    edName.setEnabled(false);
                    edPass.setEnabled(false);
                    btnLogin.setEnabled(false);

                    in = new Intent(LoginActivity.this, DrawerActivity.class);
                    in.putExtra("Username", edName.getText().toString());
                    in.putExtra("Rank", rank);
                    startActivity(in);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
