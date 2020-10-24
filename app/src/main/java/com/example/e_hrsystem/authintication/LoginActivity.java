package com.example.e_hrsystem.authintication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_hrsystem.R;

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

                Toast.makeText(LoginActivity.this, username + " " + password, Toast.LENGTH_SHORT).show();
                // TODO handle this variables
            }
        });
    }
}