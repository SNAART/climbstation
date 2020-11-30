package com.example.climbstation.retrofit


import com.example.climbstation.ConnectionInfo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    fun login(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.login(connectionData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onFailure(call: Call<ConnectionInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ConnectionInfo>,
                    response: Response<ConnectionInfo>
                ) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    fun operate(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.operate(connectionData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onResponse(
                    call: Call<ConnectionInfo>,
                    response: Response<ConnectionInfo>
                ) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }

                override fun onFailure(call: Call<ConnectionInfo>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }



   }
