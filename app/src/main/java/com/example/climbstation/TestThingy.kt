package com.example.climbstation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.Error

class TestThingy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        btn_test.setOnClickListener {
            addDummyUser()

        }
    }

    fun addDummyUser() {
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(
            "2a",
            1,
            "admin",
            "20110001",
            "CLIMBSTATION",
            null,
            null
        )

        apiService.login(userInfo) {
            Log.d("hmm","pääseekö tääne")
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("http", it.response)
            } else {
                Log.d("error", "Error registering new user")



            }


        }
    }
}