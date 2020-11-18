package com.example.e_hrsystem.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.e_hrsystem.MainActivityAdmin
import com.example.e_hrsystem.employee.EmployeeActivity
import com.example.e_hrsystem.R
import com.example.e_hrsystem.authentication.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val top = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bot = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        val splashtext = findViewById<TextView>(R.id.tvsplashtext)
        val hricon = findViewById<ImageView>(R.id.ivhricon)

        splashtext.startAnimation(top)
        hricon.startAnimation(bot)


        Handler().postDelayed({
            // if user is logged in then open the main activity, else open login activity
            if (SharedPreferencesHelper.isUserLoggedIn(this)) {
                val isAdmin = false
                if(isAdmin){
                    startActivity(Intent(this@SplashActivity, MainActivityAdmin::class.java))
                }else{
                    startActivity(Intent(this@SplashActivity, EmployeeActivity::class.java))
                }
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }, 3500)
    }
}