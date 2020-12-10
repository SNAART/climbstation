package com.example.climbstation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FirebaseRepo {

    private val fireBaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    //firebaseAuth
    fun getUser():String?{
        return fireBaseAuth.currentUser?.email
    }
    //firestore
    fun getData():Task<QuerySnapshot>{
        return fireBaseFirestore
            .collection("climb_data")
            .whereEqualTo("email", getUser()) //user email here
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
    }
    fun getLatestData(): Task<QuerySnapshot> {
        return fireBaseFirestore
            .collection("climb_data")
            .whereEqualTo("email", getUser()) //user email here
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendData(email: String, climbTime: Long, difficulty: String, length: Int, speed: Int,totalLength:Int){
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val climbDate = current.format(formatter)

        val climbItem = ClimbItem(email, climbTime, difficulty,
            length, speed, climbDate,totalLength)
        fireBaseFirestore.collection("climb_data").document().set(climbItem)
    }
}