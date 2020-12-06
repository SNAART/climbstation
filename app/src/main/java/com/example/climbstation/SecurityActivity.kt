package com.example.climbstation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.activity_security.*

class SecurityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val bol = shred.getBoolean("element", null == true)

        if (bol){
            unClickAbe()
            function_switch.isChecked = bol

        }

        set_password.setOnClickListener {
            startActivity(Intent(this, PasswordActivity::class.java))
        }

        btn_remove_password.setOnClickListener {
            val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = spassword.edit()
            editor.putString("code", "")
            editor.apply()
            Toast.makeText(this, "Password is removed now", Toast.LENGTH_SHORT).show()
        }
        function_switch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
         if (b){
             unClickAbe()
         }else clickAble()
        }
    }

    fun unClickAbe() {
        full_range.isClickable = false
        full_range.isEnabled = false

        limited_range.isClickable = false
        limited_range.isEnabled = false

        range1.isClickable = false
        range1.isEnabled = false

        range2.isClickable = false
        range2.isEnabled = false

        set_password.isClickable = true
        set_password.isEnabled = false

        btn_remove_password.isClickable = false
        btn_remove_password.isEnabled = false

        limit1.isClickable = false
        limit1.isEnabled = false

        limit2.isClickable = false
        limit2.isEnabled = false

        btn_apply_changes.isClickable = false
        btn_apply_changes.isEnabled = false
        security_elements.alpha= 0.4F
        save(true)

    }

    fun clickAble() {
        full_range.isClickable = true
        full_range.isEnabled = true

        limited_range.isClickable = true
        limited_range.isEnabled = true

        range1.isClickable = true
        range1.isEnabled = true

        range2.isClickable = true
        range2.isEnabled = true

        set_password.isClickable = true
        set_password.isEnabled = true

        btn_remove_password.isClickable = true
        btn_remove_password.isEnabled = true

        limit1.isClickable = true
        limit1.isEnabled = true

        limit2.isClickable = true
        limit2.isEnabled = true

        btn_apply_changes.isClickable = true
        btn_apply_changes.isEnabled = true
        security_elements.alpha= 1F
        save(false)
    }
    fun save(bol:Boolean){
        val spassword :SharedPreferences=getSharedPreferences("pref", MODE_PRIVATE)
        val editor:SharedPreferences.Editor=spassword.edit()
        editor.putBoolean("element",bol)
        editor.apply()
    }
}