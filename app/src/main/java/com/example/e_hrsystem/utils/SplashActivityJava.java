package com.example.e_hrsystem.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_hrsystem.admin.MainAdminActivity;
import com.example.e_hrsystem.R;
import com.example.e_hrsystem.authentication.LoginActivity;
import com.example.e_hrsystem.employee.MainEmployeeActivity;
import com.example.e_hrsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivityJava extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbRef;
    TextView splashText;
    ImageView hrIcon;
    User user;
    boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_java);


        Animation top = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Animation bot = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

         splashText = findViewById(R.id.tvsplashtext);
         hrIcon = findViewById(R.id.ivhricon);

                splashText.startAnimation(top);
                hrIcon.startAnimation(bot);

        auth = FirebaseAuth.getInstance();
        user = new User();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser()!=null){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                    getAdmin();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isAdmin) {
                                startActivity(new Intent(SplashActivityJava.this, MainAdminActivity.class));
                                finish();

                            }else{
                                startActivity(new Intent(SplashActivityJava.this, MainEmployeeActivity.class));
                                finish();
                            }

                        }
                    },1500);
                }else{

                    startActivity(new Intent(SplashActivityJava.this, LoginActivity.class));
                    finish();
                }
            }
        },3000);
    }



    private void getAdmin(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                assert user != null;
                isAdmin = user.isAdmin();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}