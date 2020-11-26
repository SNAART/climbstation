package com.example.climbstation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_test.*

class TestThingy: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        btn_test.setOnClickListener{
            addDummyUser()

        }
    }
    fun addDummyUser() {
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(  packetId = "2a",
            packetNumber = 1,
            userId = "user",
            serialNo = "xxxxx",
            password = "climbstation" ,
            response = null,
            clientKey = null)

        apiService.login(userInfo) {
            if (it?.response != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("aa",it.toString())
            } else {
                Log.d("E","Error")
            }
        }
    }
}