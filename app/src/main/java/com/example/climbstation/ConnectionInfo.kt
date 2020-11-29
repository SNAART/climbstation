package com.example.climbstation

import com.google.gson.annotations.SerializedName

data class ConnectionInfo (
    @SerializedName("PacketID") val packetId: String?,
    @SerializedName("PacketNumber") val packetNumber: Int?,
    @SerializedName("userID") val userId: String?,
    @SerializedName("ClimbstationSerialNo”") val serialNo: String?,
    @SerializedName("password") val password: String?,

    @SerializedName("Response") val response: String?,
    @SerializedName("ClientKey") val clientKey: String?



        )