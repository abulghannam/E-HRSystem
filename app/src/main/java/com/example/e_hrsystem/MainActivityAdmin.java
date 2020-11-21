package com.example.e_hrsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_hrsystem.admin.AddEmployeeActivity;
import com.example.e_hrsystem.admin.DeleteEmployeeActivity;
import com.example.e_hrsystem.authentication.LoginActivity;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;
import com.example.e_hrsystem.utils.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        // init the listeners
        initListeners();

        // get the saved name and display it
//        String name = SharedPreferencesHelper.getSavedName(this);
//        ((TextView)findViewById(R.id.textView)).setText(name);
    }

    private void initListeners() {
        findViewById(R.id.btnDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when delete employee button clicked, open the desired activity
                startActivity(new Intent(MainActivityAdmin.this, DeleteEmployeeActivity.class));
            }
        });
        findViewById(R.id.btnAddEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when add employee button clicked, open the desired activity
                startActivity(new Intent(MainActivityAdmin.this, AddEmployeeActivity.class));
            }
        });

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clear the saved name that is saved in the SP, and redirect the user to the splash screen
                //SharedPreferencesHelper.logout(MainActivityAdmin.this);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivityAdmin.this, SplashActivity.class));
                finish();
            }
        });
    }
}