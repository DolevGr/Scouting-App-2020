package com.example.primo2020v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edName, edPass;
    Button btnLogin;
    Intent i;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        context = getApplicationContext();

        btnLogin.setOnClickListener( this);
        //Toast.makeText(context.getApplicationContext(), "Creating...", Toast.LENGTH_LONG).show();
        Log.d("ONCREATE LoginActivity", "onCereate: Entered onCreate ****************************************************");
    }

    @Override
    public void onClick(View view) {
        Log.d("ONCLICK", "onClick: Entered onClick **********************************************************");

        switch (view.getId()){
            case R.id.btnLogin:
                i = new Intent(LoginActivity.this, MainActivity.class);
                break;
            default:
                break;
        }

        if(isValidName() && isValidPassword()){
            Toast.makeText(context, "Loging Button", Toast.LENGTH_LONG).show();
            Log.d("LOGINGIN", "onClick: Entered onClick **********************************************************");
            if (i != null){
                i.putExtra("Username", edName.getText().toString());
                startActivity(i);
            }
        } else {
            Toast.makeText(context.getApplicationContext(), "Enter Username Or Password", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidName(){
        return !edName.getText().toString().equals("");
    }

    public boolean isValidPassword(){
        return !edPass.getText().toString().equals("");
    }
}
