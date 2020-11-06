package com.example.e_hrsystem.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.User;

public class AddEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.etUserName)).getText().toString().trim();
                String email = ((EditText) findViewById(R.id.etEmail)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.etPassword)).getText().toString().trim();
                SwitchCompat switchIsHr = ((SwitchCompat) findViewById(R.id.switchIsHR));

                Toast.makeText(AddEmployeeActivity.this,
                        username + "\n" +
                                email + "\n" +
                                password + "\n" +
                                "is HR: " + switchIsHr.isChecked()
                        , Toast.LENGTH_SHORT).show();

                // TODO: handle the registration process
                registerUser(new User(username, email, switchIsHr.isChecked(), null));
            }
        });
    }

    private void registerUser(User user){
        // TODO register user in our online database
        // for example save it in the firebase database
    }
}