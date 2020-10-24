package com.example.e_hrsystem.authintication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_hrsystem.MainActivity;
import com.example.e_hrsystem.R;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.etUserName)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.etPassword)).getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // save user in shared-preferences so when user open the app next time he will be redirected to main activity
                SharedPreferencesHelper.saveName(LoginActivity.this, username);

                // TODO handle this variables
                // this is for testing only
                Toast.makeText(LoginActivity.this, username + " " + password, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}