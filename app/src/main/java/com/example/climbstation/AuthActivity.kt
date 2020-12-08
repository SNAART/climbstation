package com.example.climbstation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_password.*

class AuthActivity : AppCompatActivity() {
    private lateinit var getPassword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btn_check_password.setOnClickListener{
            getPassword = edt_check_password.text.toString().trim()
            val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
            val shredPassword = shred.getString("code", "")

            if (shredPassword == getPassword){
                startActivity(Intent(this,LoginActivity::class.java))
            }else{
                edt_check_password.error = "Please enter valid password"
                edt_check_password.requestFocus()
            }


        }
    }

}