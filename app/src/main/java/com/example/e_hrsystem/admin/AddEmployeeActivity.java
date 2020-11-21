package com.example.e_hrsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AddEmployeeActivity extends AppCompatActivity {

    private EditText rUsername, rEmail , rWorkingId , rPassword;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        rUsername = findViewById(R.id.etUserNameReg);
        rEmail = findViewById(R.id.etEmailReg);
        rWorkingId = findViewById(R.id.etWorkingIdReg);
        rPassword = findViewById(R.id.etPasswordReg);

        fAuth = FirebaseAuth.getInstance();

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

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    rEmail.setError("Please provide a valid email!");
                    rEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(workingID))
                {
                    rWorkingId.setError("Working ID is Required!");
                    rWorkingId.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    rPassword.setError("Password is Required");
                    rPassword.requestFocus();
                    return;
                }

                if(password.length()<6)
                {
                    rPassword.setError("Password must be >= 6 characters");
                    return;
                }

                //saving a user and validate
                fAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    User user = new User(username,email,workingID,password,switchIsHr.isChecked(),null);
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddEmployeeActivity.this,"Registration is completed"
                                                        , Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(AddEmployeeActivity.this,"Failed to Register,Try again!"
                                                        , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                                else
                                {
                                    Toast.makeText(AddEmployeeActivity.this,"Email is already exists, Try again!"
                                            , Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

}