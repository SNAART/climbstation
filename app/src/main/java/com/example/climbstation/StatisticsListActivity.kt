package com.example.climbstation

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.statistics_list.*
import kotlin.math.log

class StatisticsListActivity : AppCompatActivity() {


    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private var dataList:List<ClimbItem> =ArrayList()
    private val statisticsAdapter: StatisticsAdapter = StatisticsAdapter(dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics_list)

        loadPostData()

        firestore_list.layoutManager = LinearLayoutManager(this)
        firestore_list.adapter =statisticsAdapter
    }

    private fun loadPostData(){
    firebaseRepo.getData().addOnCompleteListener{
    if (it.isSuccessful){
        dataList = it.result!!.toObjects(ClimbItem::class.java)
        statisticsAdapter.postListItems = dataList
        statisticsAdapter.notifyDataSetChanged()
    } else{
        Log.d("fb","error: ${it.exception!!.message}")
    }
}
    }

   /* private fun getData() {
        Log.d("fb","getdata started")
        db.collection("climb_data")
            .whereEqualTo("email", "rasmus.karling@gmail.com") //user email here
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("fb", "${document.id} => ${document.data}")
                    tv_time.text = document.data["time"].toString()
                    tv_difficulty.text = "Difficulty was: ${document.data["difficulty"]}"
                    tv_lenght.text = document.data["lenght"].toString()
                    tv_speed.text = document.data["avgspeed"].toString()
                    tv_date.text = document.data["date"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("fb", "Error getting documents: ", exception)
            }
    }
    */
}