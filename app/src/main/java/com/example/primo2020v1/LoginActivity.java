package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText edName, edPass;
    Button btnLogin;
    Intent i;
    Context context;

    String name, password, nameFromDB, passFromDB, privillegeFromDB = "", privillege = "false";
    boolean priv = false, debug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        context = getApplicationContext();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edName.getText().toString().trim();
                password = edPass.getText().toString().trim();

                if (User.members.contains(name) && !password.equals("")) {
                    DatabaseReference dbRef = User.databaseReference.child(Keys.USERS).child(Integer.toString(User.members.indexOf(name)));
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                nameFromDB = dataSnapshot.child("name").getValue().toString();
                                passFromDB = dataSnapshot.child("password").getValue().toString();
                                privillegeFromDB = dataSnapshot.child("privilege").getValue().toString();
                                priv = (privillege.equals(privillegeFromDB) ? false : true) && User.admins.contains(name);

                            } catch (Exception e){
                                e.printStackTrace();
                                Log.d("Exception", "onDataChange: Data went missing :-(");
                            }

                            if (isValidName() && isValidPassword()) {
                                i = new Intent(LoginActivity.this, DrawerActivity.class);

                                if (i != null){
                                    i.putExtra("Username", edName.getText().toString());
                                    i.putExtra("Privilege", priv);
                                    startActivity(i);
                                }
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
            }
        });
    }

    private void checkPassAndUsername() {
        if(edName.getText().toString().equals("") || edPass.getText().toString().equals("")){
            Toast.makeText(context.getApplicationContext(), "Enter Username or Password", Toast.LENGTH_LONG).show();
            Log.d(TAG, "checkPassAndUsername: " + passFromDB);
        } else if(!isValidName() || !isValidPassword()) {
            Toast.makeText(context.getApplicationContext(), "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidName(){
        return name.equals(nameFromDB);
    }

    private boolean isValidPassword(){
        return password.toLowerCase().equals(passFromDB.toLowerCase());
    }

    private void enterOnDebug() {
        if (debug) {
            i = new Intent(LoginActivity.this, DrawerActivity.class);

            if (i != null){
                i.putExtra("Username", edName.getText().toString());
                i.putExtra("Privilege", priv);
                startActivity(i);
            }
        }
    }

}
