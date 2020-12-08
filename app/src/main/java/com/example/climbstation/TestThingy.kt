package com.example.climbstation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.Error

class TestThingy : AppCompatActivity() {
    var key: String? = null
    var speed :Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        btn_test.setOnClickListener {
            login()

        }
        btn_test2.setOnClickListener {
           operate()
        }
    }

    fun login() {
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(
            "2a",
            1,
            "admin",
            "20110001",
            "CLIMBSTATION",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        apiService.login(userInfo) {
            Log.d("hmm", "pääseekö tääne")
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
              //  Log.d("http", it.toString())
               // Log.d("http",it.clientKey)
                key = it.clientKey.toString()
                saveText("client_key", key!!)
            } else {
                Log.d("error", "Error registering new user")
            }
        }
    }
    fun saveText(key: String, value: String) {
        val spassword: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spassword.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun operate(){
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(
            "2c",
            1,
            null,
            "20110001",
            null,
            null,
            key,
            null,
            null,
            null,
            null,
            "start",
            null,
            null
        )

        apiService.operate(userInfo){
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("http", it.response)

            } else {
                Log.d("error", "Error getting info")
            }
        }

    }

    fun setSpeed(){
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(
            "2d",
            1,
            null,
            "20110001",
            null,
            null,
            key,
            null,
            null,
            null,
            null,
            null,
            speed,
            null

        )

        apiService.operate(userInfo){
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("http", it.response)

            } else {
                Log.d("error", "Error getting info")
            }
        }

    }


}