package com.example.climbstation

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {
    private lateinit var password:String
    private lateinit var confirmPassword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        btn_save_password.setOnClickListener{
            if(checkFields()){
                val spassword :SharedPreferences=getSharedPreferences("pref", MODE_PRIVATE)
                val editor:SharedPreferences.Editor=spassword.edit()
                editor.putString("code",password)
                editor.apply()
                finish()

            }


        }

        btn_cancel.setOnClickListener{

            finish()
        }
    }



    fun checkFields():Boolean{
        password= edt_get_password.text.toString().trim()
        confirmPassword=edt_get_confirm_password.text.toString().trim()

        if (password.isEmpty()) {
            edt_get_password.error = "Please enter password"
            edt_get_password.requestFocus()
            return false
        }
        else if (confirmPassword.isEmpty()) {
            edt_get_confirm_password.error = "Please confirm password"
            edt_get_confirm_password.requestFocus()
            return false
        }else if (password!=confirmPassword) {
            edt_get_confirm_password.error = "Password not match"
            edt_get_confirm_password.requestFocus()
            return false
        }else
            return true
    }
}