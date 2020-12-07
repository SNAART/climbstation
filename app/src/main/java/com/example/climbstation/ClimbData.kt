package com.example.climbstation

data class ClimbData(
    var climbList: ArrayList<ClimbItem> = arrayListOf()
)

data class ClimbItem(
    var email: String? = null,
    var climbTime: String? = null,
    var difficulty: String? = null,
    var lenght: String? = null,
    var avgSpeed: String? = null,
    var date : String? = null
)