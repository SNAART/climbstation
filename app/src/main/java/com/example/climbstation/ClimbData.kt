package com.example.climbstation

import java.util.*

/*data class ClimbData(
   // var climbList: ArrayList<ClimbItem> = arrayListOf()
    var climbList: MutableList<ClimbItem> = java.util.ArrayList()
)
 */

data class ClimbItem(
    var email: String? = null,
    var climbTime: String? = null,
    var difficulty: String? = null,
    var lenght: String? = null,
    var avgSpeed: String? = null,
    var date : Date? = null
)