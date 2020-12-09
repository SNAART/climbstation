package com.example.climbstation

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_edit_terrins.*
import kotlinx.android.synthetic.main.activity_security.*
/*
class EditTerrins : AppCompatActivity() {
    private lateinit var speed: String
    private lateinit var angle: String
    private lateinit var length: String
    private lateinit var client_key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_terrins)
        get_speed.setText(getValues("speed"))
        get_angle.setText(getValues("angle"))
        get_length.setText(getValues("length"))



        btn_terrins.setOnClickListener {
            if (checkFields()) {
                save("speed", speed)
                sendSpeed()
                getInfo()

                save("length", length)
                var intAngle = Integer.parseInt(angle)
                if (intAngle <= 45 && intAngle >= -45) {

                    val oldAngle = getValues("angle")
                    save("oldAngle", oldAngle)
                    save("angle", angle)
                    sendAngle()
                } else {
                    get_angle.error = "enter angle between (-45)-(+45)"
                    get_angle.requestFocus()
                }
                Toast.makeText(this, "values are saved", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
    }

    private fun sendAngle() {
        val apiService = RestApiService()
        client_key = getValues("client_key")
        Log.d("TAG", "sendAngle: client key " + client_key)

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
            null)

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
            null,
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

    private fun sendSpeed() {
        val apiService = RestApiService()
        client_key = getValues("client_key")
        Log.d("TAG", "sendAngle: client key " + client_key)

        val userInfo = ConnectionInfo(
            "2d",
            1,
            null,
            "20110001",
            null,
            null,
            client_key,
            null,
            null,
            null,
            null,
            null,
            speed
        )

        apiService.setSpeed(userInfo) {
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                //  Log.d("http", it.toString())
                // Log.d("http",it.clientKey)
                Log.d("TAG", "sendSpeed: " + it.response)

            } else {
                Log.d("error", "Error registering new user")
            }
        }
    }

    fun save(key: String, value: String) {
        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun checkFields(): Boolean {
        speed = get_speed.text.toString().trim()
        angle = get_angle.text.toString().trim()
        length = get_length.text.toString().trim()


        when {
            speed.isEmpty() -> {
                get_speed.error = "Please enter speed"
                get_speed.requestFocus()
                return false
            }

            angle.isEmpty() -> {
                get_angle.error = "Please enter angle"
                get_angle.requestFocus()
                return false
            }


            length.isEmpty() -> {
                get_length.error = "Please enter limit"
                get_length.requestFocus()
                return false
            }
            else -> return true
        }
    }

    fun getValues(key: String): String {
        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        return shred.getString(key, "")!!
    }
}

 */