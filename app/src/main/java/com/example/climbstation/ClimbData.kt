package com.example.climbstation

import java.util.*

/*data class ClimbData(
   // var climbList: ArrayList<ClimbItem> = arrayListOf()
    var climbList: MutableList<ClimbItem> = java.util.ArrayList()
)
 */

data class ClimbItem(
    var email: String? = null,
    var climbTime: Long? = null,
    var difficulty: String? = null,
    var lenght: Int? = null,
    var avgSpeed: Int? = null,
    var date : String? = null
)