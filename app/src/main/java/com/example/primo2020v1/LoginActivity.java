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

import com.example.primo2020v1.libs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edName, edPass;
    Button btnLogin;
    Intent i;
    Context context;

    String name, password, nameFromDB, passFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        context = getApplicationContext();

        Log.d("ONCREATE LoginActivity", "onCereate: Entered onCreate ****************************************************");

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("ONCLICK", "onClick: Entered onClick **********************************************************");
        name = edName.getText().toString().trim();
        password = edPass.getText().toString().trim();

        if (view.getId() == R.id.btnLogin) {
            if (User.members.contains(name)) {
                Log.d("Debugging Database 1", "*********************************************************onDataChange: ");
                DatabaseReference dbreff = User.databaseReference.child("Users").child(Integer.toString(User.members.indexOf(name) + 1));
                dbreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{
                            nameFromDB = dataSnapshot.child("name").getValue().toString();
                            passFromDB = dataSnapshot.child("password").getValue().toString();
                        } catch (Exception e){
                            e.printStackTrace();
                            Log.d("Exception", "onDataChange: Data went missing :-(");
                        }

                        if(isValidName() && isValidPassword()){
                            i = new Intent(LoginActivity.this, MainActivity.class);
                            //Toast.makeText(context, "Loging Button", Toast.LENGTH_LONG).show();
                            Log.d("LOGINGIN", "onClick: Entered onClick **********************************************************");
                            if (i != null){
                                i.putExtra("Username", edName.getText().toString());
                                startActivity(i);
                            }
                        }
                        Log.d("From databse: ", "onDataChange: " + nameFromDB + " : " + passFromDB);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            checkPassAndUsername();
        }

    }

    private void checkPassAndUsername() {
        if(edName.getText().toString().equals("") || edPass.getText().toString().equals("")){
            Toast.makeText(context.getApplicationContext(), "Enter Username or Password", Toast.LENGTH_LONG).show();
        } else if(!isValidName() || !isValidPassword()) {
            Toast.makeText(context.getApplicationContext(), "Username or Password is incorrect", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidName(){
        return edName.getText().toString().equals(nameFromDB);
    }

    public boolean isValidPassword(){
        return edPass.getText().toString().equals(passFromDB);
    }

}
