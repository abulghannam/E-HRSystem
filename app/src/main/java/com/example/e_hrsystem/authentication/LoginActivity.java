package com.example.e_hrsystem.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_hrsystem.MainActivityAdmin;
import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employee.EmployeeActivity;
import com.example.e_hrsystem.model.TimeLog;
import com.example.e_hrsystem.model.User;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    EditText etEmail,etPassword;
    FirebaseAuth auth;
    Button btnLogin;
    DatabaseReference dbRef;
    boolean isAdmin;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();
        user = new User();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });
    }

    private void startLogin(){
        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();

        if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)){
            auth.signInWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                            getAdmin();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isAdmin){
                                        startActivity(new Intent(LoginActivity.this, MainActivityAdmin.class));
                                        finish();

                                    }else{
                                        startActivity(new Intent(LoginActivity.this, EmployeeActivity.class));
                                        finish();

                                    }

                                }
                            },1000);
                        }
                    
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "all field is ...", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAdmin(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);

                isAdmin = user.isAdmin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    private void initViews() {
//        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String username = ((EditText) findViewById(R.id.etUsernameLogin)).getText().toString().trim();
//                String password = ((EditText) findViewById(R.id.etPasswordLogin)).getText().toString().trim();
//
//                //Username and Password Validation
//                if (username.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                Intent intent = null;
//                User user = new User();
//
//                if (username.equals("employee") &&
//                        password.equals("employee123")) {
//                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(LoginActivity.this, EmployeeActivity.class);
//
//                    user.setEmail("employee@test.com");
//                    user.setUsername("employee");
//                    ArrayList<TimeLog> list = new ArrayList<>();
//                    list.add(new TimeLog("date1", "9:00 am", "6:00 pm"));
//                    list.add(new TimeLog("date2", "9:10 am", "6:10 pm"));
//                    list.add(new TimeLog("date3", "9:20 am", "6:20 pm"));
//                    list.add(new TimeLog("date4", "9:30 am", "6:30 pm"));
//                    user.setHistory(list);
//
//                }
//                if (username.equals("admin") &&
//                        password.equals("admin123")) {
//                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
//
//                    user.setEmail("admin@test.com");
//                    user.setUsername("admin");
//                    user.setAdmin(true);
//                }
//
//                SharedPreferencesHelper.saveUser(LoginActivity.this, user);
//                startActivity(intent);
//                finish();
//
//
//                // save user in shared-preferences so when user open the app next time he will be redirected to main activity
//                // TODO  Request login from server and save the logged in user
//
//                // TODO handle this variables
//                // this is for testing only
//                Toast.makeText(LoginActivity.this, "Welcome Back" + " " + username, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

}