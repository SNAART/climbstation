package com.example.climbstation

import com.google.gson.annotations.SerializedName

data class ConnectionInfo (
    @SerializedName("PacketID") val packetId: String?,
    @SerializedName("PacketNumber") val packetNumber: Int?,
    @SerializedName("userID") val userId: String?,
    @SerializedName("ClimbstationSerialNo") val serialNo: String?,
    @SerializedName("password") val password: String?,

    @SerializedName("Response") val response: String?,
    @SerializedName("clientKey") val clientKey: String?,

    @SerializedName("ClimbingData") val climbingData: String?,

    @SerializedName("speed") val speedNow: String?,
    @SerializedName("angle") val angleNow: String?,
    @SerializedName("Lenght") val length: String?,

    @SerializedName("Operation") val operation: String?,

    @SerializedName("Speed") val speed: String?




        )