package com.example.e_hrsystem.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailForgotPass;
    Button btnResetPass;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailForgotPass = findViewById(R.id.etForgotEmail);
        btnResetPass = findViewById(R.id.btnResetPassword);
        progressBar = findViewById(R.id.progressbarlogin);

        auth = FirebaseAuth.getInstance();

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = emailForgotPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailForgotPass.setError("Email is Required!");
            emailForgotPass.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailForgotPass.setError("Please provide a valid email!");
            emailForgotPass.requestFocus();
        }

        else {

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password",
                                Toast.LENGTH_LONG).show();
                        emailForgotPass.setText("");
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                        emailForgotPass.setText("");
                    }
                }
            });
        }
    }
}