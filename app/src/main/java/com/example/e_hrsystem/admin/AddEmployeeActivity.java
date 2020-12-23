package com.example.e_hrsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class AddEmployeeActivity extends AppCompatActivity {
    String gender = null;

    private EditText rUsername, rEmail, rWorkingId, rPassword;
    private RadioGroup radioGroup;
    RadioButton male, female, radioButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        rUsername = findViewById(R.id.etUserNameReg);
        rEmail = findViewById(R.id.etEmailReg);
        rWorkingId = findViewById(R.id.etWorkingIdReg);
        rPassword = findViewById(R.id.etPasswordReg);
        radioGroup = findViewById(R.id.rbGroup);
        male = findViewById(R.id.rbMale);
        female = findViewById(R.id.rbFeMale);


        auth = FirebaseAuth.getInstance();

        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = rUsername.getText().toString().trim();
                final String email = rEmail.getText().toString().trim();
                final String workingID = rWorkingId.getText().toString().trim();
                final String password = rPassword.getText().toString().trim();
                final SwitchCompat switchIsHr = findViewById(R.id.switchIsHR);

                final int radioId = radioGroup.getCheckedRadioButtonId();


                radioButton = findViewById(radioId);
                if (radioButton != null) {
                    if (radioButton.getText().equals("Male")) {
                        gender = "male";
                    } else if (radioButton.getText().equals("Female")) {
                        gender = "female";

                    }
                } else {
                    Toast.makeText(AddEmployeeActivity.this, "please check the gender", Toast.LENGTH_SHORT).show();
                }


                //validation of registration form
                if (TextUtils.isEmpty(username)) {
                    rUsername.setError("Username is Required!");
                    rUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Email is Required!");
                    rEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    rEmail.setError("Please provide a valid email!");
                    rEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(workingID)) {
                    rWorkingId.setError("Working ID is Required!");
                    rWorkingId.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is Required");
                    rPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    rPassword.setError("Password must be >= 6 characters");
                    return;
                }
                final boolean isAdmin;
                if (switchIsHr.isChecked()) {

                    isAdmin = true;
                } else {
                    isAdmin = false;
                }

                final Query queryWorkingID = FirebaseDatabase.getInstance().getReference("users").orderByChild("workingID").equalTo(workingID);
                Query queryUserName = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);

                queryUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(AddEmployeeActivity.this, "Username is already exist!", Toast.LENGTH_SHORT).show();
                        }else{
                            queryWorkingID.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(AddEmployeeActivity.this, "Working ID is already exist!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        auth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            User user = new User(username, email, workingID, isAdmin, gender, false, null);
                                                            FirebaseDatabase.getInstance().getReference("users")
                                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(AddEmployeeActivity.this, "Registration is completed"
                                                                                , Toast.LENGTH_SHORT).show();
                                                                        rUsername.setText("");
                                                                        rPassword.setText("");
                                                                        rWorkingId.setText("");
                                                                        rEmail.setText("");
                                                                        switchIsHr.setChecked(false);
                                                                        radioGroup.clearCheck();
                                                                        auth.signOut();
                                                                        auth.signInWithEmailAndPassword("abood.gh@live.com","Aboodgh22");

                                                                    } else {
                                                                        Toast.makeText(AddEmployeeActivity.this, "Failed to Register,Try again!"
                                                                                , Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });

                                                        } else {
                                                            Toast.makeText(AddEmployeeActivity.this, "Email is already exists, Try again!"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //saving a user and validate
            }
        });
    }

}