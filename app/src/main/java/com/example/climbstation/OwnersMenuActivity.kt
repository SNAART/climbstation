package com.example.climbstation

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
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
        if (restore == "1") {
            restore_angle.isChecked = true
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

}