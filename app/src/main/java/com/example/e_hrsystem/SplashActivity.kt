package com.example.e_hrsystem

import android.app.backup.SharedPreferencesBackupHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.e_hrsystem.authintication.LoginActivity
import com.example.e_hrsystem.utils.SharedPreferencesHelper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // if user is logged in then open the main activity, else open login activity
            if (SharedPreferencesHelper.isUserLoggedIn(this)) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}