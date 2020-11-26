package com.example.climbstation.retrofit

import com.example.climbstation.ConnectionInfo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    fun login(userData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.login(userData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onFailure(call: Call<ConnectionInfo>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<ConnectionInfo>, response: Response<ConnectionInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}