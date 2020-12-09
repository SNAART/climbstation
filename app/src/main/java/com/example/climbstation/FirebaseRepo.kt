package com.example.climbstation

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import java.time.Instant
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

    fun sendData(email: String, climbTime: Long, difficulty: String, length: Int, speed: Int){
        var climbDate = Date()

        val climbItem = ClimbItem(email, climbTime, difficulty,
            length, speed, climbDate)
        fireBaseFirestore.collection("climb_data").document().set(climbItem)
    }
}