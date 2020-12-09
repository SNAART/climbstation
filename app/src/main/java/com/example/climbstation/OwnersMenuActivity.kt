package com.example.climbstation

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.climbstation.retrofit.RestApiService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_owners_menu.*

class OwnersMenuActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners_menu)
        firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth?.currentUser?.email
        user_email.text = email
        getId()
        setMeasurement()
        setModes()
        setReport()
        val restore = getValues("angleRestore")
        val adjustValue = getValues("adjustValue")
        adjust_loc_value.setText(adjustValue)

        val restoreLength = getValues("lengthRestore")
        if (restore == "1") {
            restore_angle.isChecked = true
        }
        if (restoreLength == "1") {
            loc.isChecked = true
        }

        restore_angle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val oldAngle = getValues("oldAngle")
                save("angle", oldAngle)
                save("angleRestore", "1")

                Toast.makeText(this, "angle value changed now", Toast.LENGTH_SHORT).show()
            } else {
                save("angleRestore", "0")


            }

        }
        loc.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                val oldLength = getValues("oldLength")
                save("length", oldLength)
                save("lengthRestore", "1")
                Toast.makeText(this, "length value changed now", Toast.LENGTH_SHORT).show()
            } else {
                save("lengthRestore", "0")
            }

        }
        save_it.setOnClickListener {
                val value= adjust_loc_value.text.toString().trim()
            val intValue=Integer.parseInt(value)

            if (value.isEmpty()){
                adjust_loc_value.error="missing value"
                adjust_loc_value.requestFocus()
            }else if (intValue !in 0..20){
                adjust_loc_value.error="value should in 0-20"
                adjust_loc_value.requestFocus()
            }else{
                save("adjustValue",value)
                getInfo()
                finish()
            }


        }
    }


    fun save(key: String, value: String) {
        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getId() {

        measurement.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (i) {
                R.id.meter -> save("measurement", meter.text.toString())
                R.id.foot -> save("measurement", foot.text.toString())

            }
        }
        modes.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (i) {
                R.id.next_level -> save("modes", next_level.text.toString().trim())
                R.id.random -> save("modes", random.text.toString().trim())
                R.id.repeat -> save("modes", repeat.text.toString().trim())
                R.id.slow_down -> save("modes", slow_down.text.toString().trim())

            }
        }
        email_reporting.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (i) {
                R.id.monthly -> save("report", monthly.text.toString())
                R.id.weekly -> save("report", weekly.text.toString())

            }
        }
    }

    fun setMeasurement() {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val bol = shred.getString("measurement", "")
        scale.text = bol
        if (bol == "Meter") {
            meter.isChecked = true

        } else {
            foot.isChecked = true
        }
    }

    fun setModes() {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        when (shred.getString("modes", "")) {
            "Random" -> {
                random.isChecked = true
            }
            "Repeat" -> {
                repeat.isChecked = true
            }
            "Slow Down" -> {
                slow_down.isChecked = true
            }
            else -> {
                next_level.isChecked = true
            }
        }
    }

    fun setReport() {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val bol = shred.getString("report", "")
        if (bol == "Monthly") {
            monthly.isChecked = true
        } else {
            weekly.isChecked = true
        }
    }

    fun getValues(key: String): String {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        return shred.getString(key, "")!!
    }
    private fun getInfo() {
        val apiService = RestApiService()
       val client_key = getValues("client_key")
        Log.d("TAG", "sendAngle: client key $client_key")

        val userInfo = ConnectionInfo(
            "2b",
            1,
            null,
            "20110001",
            null,
            null,
            client_key,
            "request",
            null,
            null,
            null,
            null,
            null,
            null)

        apiService.getInfo(userInfo) {
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                //  Log.d("http", it.toString())
                // Log.d("http",it.clientKey)
                Log.d("TAG", "sendLength: " + it.response)

            } else {
                Log.d("error", "Error registering new user")
            }
        }
    }

}