package com.example.climbstation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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
            .whereEqualTo("email", "rasmus.karling@gmail.com") //user email here
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
    }

    //Remember to add Date back
    fun sendData(email: String, climbTime: Long, difficulty: String, length: Int, speed: Int){
        val climbItem = ClimbItem(email, climbTime, difficulty,
            length, speed)
        fireBaseFirestore.collection("climb_data").document().set(climbItem)
    }
}