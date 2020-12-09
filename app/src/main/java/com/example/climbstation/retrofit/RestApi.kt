package com.example.climbstation.retrofit

import com.example.climbstation.ConnectionInfo
import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>

    @Headers("Content-Type: application/json")
    @POST("climbstationinfo")
    fun getInfo(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>

    @Headers("Content-Type: application/json")
    @POST("Operation")
    fun operate(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>

    @Headers("Content-Type: application/json")
    @POST("setspeed")
    fun setSpeed(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>

    @Headers("Content-Type: application/json")
    @POST("setangle")
    fun setAngle(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>

    @Headers("Content-Type: application/json")
    @POST("logout")
    fun logout(@Body connectionData: ConnectionInfo): Call<ConnectionInfo>



}