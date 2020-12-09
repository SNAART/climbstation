package com.example.climbstation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import com.google.protobuf.Internal
import kotlinx.android.synthetic.main.activity_security.*

class SecurityActivity : AppCompatActivity() {
    private lateinit var Range1 :String
    private lateinit var Range2 :String
    private lateinit var Limit1 :String
    private lateinit var Limit2 :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val bol = shred.getBoolean("element", null == true)
        val box = shred.getBoolean("box", null == true)

        range1.setText(getValues("range1"))
        range2.setText(getValues("range2"))
        limit1.setText(getValues("limit1"))
        limit2.setText(getValues("limit2"))

        if (bol) {
            unClickAbe()
            function_switch.isChecked = bol

        }
        if (box) {
            full_range.isChecked = true

            range1.isClickable = false
            range1.isEnabled = false

            range2.isClickable = false
            range2.isEnabled = false

            range1.alpha = 0.4f
            range2.alpha = 0.4f
        } else  {
            limited_range.isChecked=true

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
            if (b) {
                unClickAbe()
            } else clickAble()
        }
        full_range.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b){
                save("box", true)
                if (limited_range.isChecked){
                    limited_range.isChecked = false
                }
                range1.isClickable = false
                range1.isEnabled = false

                range2.isClickable = false
                range2.isEnabled = false

                range1.alpha=0.4f
                range2.alpha=0.4f
            }else if(!b){
                save("box", false)
                limited_range.isChecked=true
                range1.isClickable = true
                range1.isEnabled = true

                range2.isClickable = true
                range2.isEnabled = true
                range1.alpha = 1f
                range2.alpha = 1f


            }

        }
        limited_range.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                save("box", false)
                if (full_range.isChecked) {
                    full_range.isChecked = false
                }

                range1.isClickable = true
                range1.isEnabled = true

                range2.isClickable = true
                range2.isEnabled = true
                range1.alpha = 1f
                range2.alpha = 1f
            }
            else{

                save("box", true)
                full_range.isChecked=true
                range1.isClickable = false
                range1.isEnabled = false
                range2.isClickable = false
                range2.isEnabled = false
                range1.alpha=0.4f
                range2.alpha=0.4f
            }


        }

        btn_apply_changes.setOnClickListener{
            if (checkFields()){
                saveText("range1",Range1)
                saveText("range2",Range2)
                saveText("limit1",Limit1)
                saveText("limit2",Limit2)
                Toast.makeText(this, "values saved", Toast.LENGTH_SHORT).show()
                finish()

            }
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
        security_elements.alpha = 0.4F
        save("element", true)

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
        security_elements.alpha = 1F
        save("element", false)
    }

    fun save(key: String, bol: Boolean) {
        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putBoolean(key, bol)
        editor.apply()
    }
    fun saveText(key: String, value: String) {
        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun checkFields():Boolean{
        Range1= range1.text.toString().trim()
        Range2=range2.text.toString().trim()
        Limit1=limit1.text.toString().trim()
        Limit2=limit2.text.toString().trim()
        val intRange1= Integer.parseInt(Range1)
        val intRange2= Integer.parseInt(Range2)

        when {
            Range1.isEmpty() -> {
                range1.error = "Please enter range"
                range1.requestFocus()
                return false
            }
            Range2.isEmpty() -> {
                range2.error = "Please enter range"
                range2.requestFocus()
                return false
            }
            Limit1.isEmpty() -> {
                limit1.error = "Please enter limit"
                limit1.requestFocus()
                return false
            }
            intRange1 !in -45..0 ->{
                range1.error = "enter range -45 to 0 "
                range1.requestFocus()
                return false
            }
            Limit2.isEmpty() -> {
                limit2.error = "Please enter limit"
                limit2.requestFocus()
                return false
            }
            intRange2 !in 0..45 ->{
                range2.error = "enter range 0..45"
                range2.requestFocus()
                return false
            }

            else -> return true
        }
    }

    fun getValues(key:String):String{
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        return shred.getString(key, "")!!
    }
}