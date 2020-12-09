package com.example.climbstation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_calibration_menu.*
import kotlinx.android.synthetic.main.activity_edit_terrins.*

class CalibrationMenuActivity : AppCompatActivity() {
    private var num: Int = 0
    private lateinit var distance: String
    private lateinit var cal: String

    private lateinit var client_key: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calibration_menu)
        txNumbers.text = getValues("angle")
        scale.text = getValues("measurement")
        val time= getTime("time")
        val elapsedTime: Long = System.currentTimeMillis() - time
        val exactTime=elapsedTime*(7* 70 *3.5)/ 200
        get_distance.setText(getValues("distance"))
       cal_scale.setText(exactTime.toString())


        btn_plus.setOnClickListener {
            if (num <= 44) {
                num++
                setText()
            } else {
                Toast.makeText(this, "angle cant be greater than 45", Toast.LENGTH_SHORT).show()
            }


        }
        btn_minus.setOnClickListener {
            if (num >= -44) {
                num--
                setText()
            } else {
                Toast.makeText(this, "angle cant be less than -45", Toast.LENGTH_SHORT).show()
            }

        }
        saveChanges.setOnClickListener {
            if (checkFields()) {

                val angle = getValues("angle")
                save("oldAngle", angle)
                save("angle", num.toString())
                save("distance", distance)
                sendAngle()
                getInfo()
                Toast.makeText(this, "values saved", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private fun sendAngle() {
        val apiService = RestApiService()
        client_key = getValues("client_key")
        Log.d("TAG", "sendAngle: client key " + client_key)
        var angle = num.toString()
        val userInfo = ConnectionInfo(
            "2e",
            1,
            null,
            "20110001",
            "CLIMBSTATION",
            null,
            client_key,
            null,
            null,
            angle,
            null,
            null,
            null,
            angle
        )

        apiService.setAngle(userInfo) {
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                //  Log.d("http", it.toString())
                // Log.d("http",it.clientKey)
                Log.d("TAG", "sendAngle: " + it.response)

            } else {
                Log.d("error", "Error registering new user")
            }
        }
    }

    fun setText() {
        txNumbers.text = num.toString() + ""
    }

    fun getValues(key: String): String {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        return shred.getString(key, "")!!
    }
    fun getTime(key: String): Long {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        return shred.getLong(key, 0)!!
    }
    fun checkFields(): Boolean {
        distance = get_distance.text.toString().trim()
        num = Integer.parseInt(txNumbers.text.toString().trim())
        cal = cal_scale.text.toString().trim()
        var intDistance = Integer.parseInt(distance)

        when {
            distance.isEmpty() -> {
                get_distance.error = "Please enter distance"
                get_distance.requestFocus()
                return false
            }
            intDistance !in 0..20 ->{
                get_distance.error = "Please enter distance 0-20"
                get_distance.requestFocus()
                return false

            }

            num == null -> {
                txNumbers.error = "Please enter angle"
                txNumbers.requestFocus()
                return false
            }

            else -> return true
        }
    }

    fun save(key: String, value: String) {

        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putString(key, value)
        editor.apply()
    }
    private fun getInfo() {
        val apiService = RestApiService()
        client_key = getValues("client_key")
        Log.d("TAG", "sendAngle: client key " + client_key)

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
            distance,
            null,
            null,
            null
        )

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