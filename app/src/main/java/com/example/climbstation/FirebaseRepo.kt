package com.example.climbstation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepo {

    private val fireBaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    //firebaseAuth
    fun getUser():FirebaseUser?{
        return fireBaseAuth.currentUser
    }
    //firestore
    fun getData():Task<QuerySnapshot>{
        return fireBaseFirestore
            .collection("climb_data")
            .whereEqualTo("email", "rasmus.karling@gmail.com") //user email here
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
    }
}