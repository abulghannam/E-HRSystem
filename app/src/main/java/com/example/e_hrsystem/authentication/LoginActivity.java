package com.example.e_hrsystem.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_hrsystem.MainActivity2;
import com.example.e_hrsystem.MainActivityAdmin;
import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.TimeLog;
import com.example.e_hrsystem.model.User;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

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

                //Username Validation
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Password Validation
                if (password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = null;
                User user = new User();

                if (username.equals("employee") &&
                        password.equals("employee123")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity2.class);

                    user.setEmail("employee@test.com");
                    user.setUsername("employee");
                    ArrayList<TimeLog> list = new ArrayList<>();
                    list.add(new TimeLog("date1", "9:00 am", "6:00 pm"));
                    list.add(new TimeLog("date2", "9:10 am", "6:10 pm"));
                    list.add(new TimeLog("date3", "9:20 am", "6:20 pm"));
                    list.add(new TimeLog("date4", "9:30 am", "6:30 pm"));
                    user.setHistory(list);

                }
                if (username.equals("admin") &&
                        password.equals("admin123")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getApplicationContext(), MainActivityAdmin.class);

                    user.setEmail("admin@test.com");
                    user.setUsername("admin");
                    user.setAdmin(true);
                }

                SharedPreferencesHelper.saveUser(LoginActivity.this, user);
                startActivity(intent);
                finish();


                // save user in shared-preferences so when user open the app next time he will be redirected to main activity
                // TODO  Request login from server and save the logged in user

                // TODO handle this variables
                // this is for testing only
                Toast.makeText(LoginActivity.this, "Welcome Back" + " " + username, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void test() {
        String jsonString = "{ \"username\": \"Burini\", \"isAdmin\": true, \"history\": [ { \"date\": \"3-11-2020\", \"checkIn\": \"9:00 am\", \"checkOut\": \"6:00 pm\" }, { \"date\": \"4-11-2020\", \"checkIn\": \"9:00 am\", \"checkOut\": \"6:00 pm\" }, { \"date\": \"5-11-2020\", \"checkIn\": \"9:00 am\", \"checkOut\": \"6:00 pm\" }, { \"date\": \"6-11-2020\", \"checkIn\": \"9:00 am\", \"checkOut\": \"6:00 pm\" } ] }";

        User user = new Gson().fromJson(jsonString, User.class);

        //////////////////////

        String sameJsonString = new Gson().toJson(user);

    }
}