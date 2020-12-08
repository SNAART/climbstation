package com.example.climbstation.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climbstation.ClimbData
import com.example.climbstation.MainActivity
import com.example.climbstation.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_statistics.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var db = FirebaseFirestore.getInstance()
    // lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser.toString()
       */

        btn_stats.setOnClickListener {
           // Log.d("fb",currentUser)
            getLatestData( )
        }

    }

    fun getLatestData() {
        Log.d("fb","getdata started")
        db.collection("climb_data")
            .whereEqualTo("email", "rasmus.karling@gmail.com") //user email here
            .orderBy("date",Query.Direction.DESCENDING)
            .limit(1)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      /*  activity.let {
            btn_stats.setOnClickListener{
                (activity as MainActivity?)!!.startList()
            }
        } */

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}