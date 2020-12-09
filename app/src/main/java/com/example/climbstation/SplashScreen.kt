package com.example.climbstation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val shred:SharedPreferences=getSharedPreferences("pref", MODE_PRIVATE)
            val password=shred.getString("code","")
            if (password.isEmpty()){
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,AuthActivity::class.java))
            }


        }, 3000)


    }
}