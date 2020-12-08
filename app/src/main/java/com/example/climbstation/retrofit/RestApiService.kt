package com.example.climbstation.retrofit

import android.util.Log

import com.example.climbstation.ConnectionInfo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    private val retrofit = ServiceBuilder.buildService(RestApi::class.java)

    fun login(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        retrofit.login(connectionData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onFailure(call: Call<ConnectionInfo>, t: Throwable) {

                    Log.d("TAG", "onFailure: Called "+t)

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
        retrofit.operate(connectionData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onResponse(
                    call: Call<ConnectionInfo>,
                    response: Response<ConnectionInfo>
                ) {
                    Log.d("TAG", "onResponse: $response")
                    val addedUser = response.body()
                    onResult(addedUser)
                }

                override fun onFailure(call: Call<ConnectionInfo>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    fun setSpeed(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        retrofit.setSpeed(connectionData).enqueue(
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

    fun setAngle(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        retrofit.setAngle(connectionData).enqueue(
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

    fun logout(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        retrofit.logout(connectionData).enqueue(
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

    fun getInfo(connectionData: ConnectionInfo, onResult: (ConnectionInfo?) -> Unit) {
        retrofit.getInfo(connectionData).enqueue(
            object : Callback<ConnectionInfo> {
                override fun onResponse(
                    call: Call<ConnectionInfo>,
                    response: Response<ConnectionInfo>
                ) {
                    Log.d("TAG", "onResponse: $response")
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
