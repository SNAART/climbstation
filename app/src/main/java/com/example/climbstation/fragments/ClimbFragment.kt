package com.example.climbstation.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.climbstation.ConnectionInfo
import com.example.climbstation.R
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.fragment_climb.*
import kotlinx.android.synthetic.main.fragment_climb.view.*
import kotlin.concurrent.timer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClimbFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClimbFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var key: String? = null
    private var started = false
    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown: Long = 60000
    private val countDownInterval: Long = 1000
    private var millisecondsPassed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                millisecondsPassed += countDownInterval
                val timeLeft = millisUntilFinished / 1000
                Log.d("asd", "onTick timeLeft: ${timeLeft}")
                Log.d("asd", "millisecondsPassed: ${millisecondsPassed}")
            }

            override fun onFinish() {
                Log.d("asd", "Timer has finished.")
            }
        }
        //renderList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        login()
        val view = inflater!!.inflate(R.layout.fragment_climb, container, false)
        // Inflate the layout for this fragment view.mylist.adapter = ArrayAdapter(this.context, android.R.layout.simple_list_item_1,listOf("car", "plane"))
        view.mode.text = "Mode: ${selectedMode}"
        view.mode.setOnClickListener{changeMode(view)}
        renderList(view)

        view.start_button.setOnClickListener {
            view.start_button.isEnabled = false
            if (key != null) operate(view)
        }

        return view
    }

    fun login() {
        val apiService = RestApiService()
        val userInfo = ConnectionInfo(
            "2a",
            1,
            "admin",
            "20110001",
            "CLIMBSTATION",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        apiService.login(userInfo) {
            Log.d("asd", "pääseekö tääne")
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                //  Log.d("http", it.toString())
                // Log.d("http",it.clientKey)
                key = it.clientKey.toString()
                Log.d("asd",it.response)
            } else {
                Log.d("asd", "Error registering new user")
            }
        }
    }

    fun operate(view: View){
        val apiService = RestApiService()
        var operation: String = "start"
        if (started == true){
            operation = "stop"
        }
        val userInfo = ConnectionInfo(
            "2c",
            1,
            null,
            "20110001",
            null,
            null,
            key,
            null,
            null,
            null,
            null,
            operation,
            null
        )

        apiService.operate(userInfo){
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("asd", "start response ${it.response}")
                if(it.response == "OK"){
                    started = !started
                    if (started) {
                        countDownTimer.start()
                        view.start_button.text = "Stop"
                    } else {
                        countDownTimer.cancel()
                        Log.d("asd", "Total climb time: ${millisecondsPassed / 1000} seconds")
                        //TODO:calculate time duration and send to statistics
                        view.start_button.text = "Start"
                    }
                }
            } else {
                Log.d("asd", "Error getting info")
            }
            view.start_button.isEnabled = true
        }

    }

    private val modeList: List<String> = arrayListOf("To next level", "Random", "Repeat", "Slow down")

    private var selectedMode: String = modeList[0]

    private fun changeMode(view: View){
        val index = modeList.indexOf(selectedMode)
        val nextIndex = (index + 1) % 4
        selectedMode = modeList[nextIndex]
        view.mode.text = "Mode: ${selectedMode}"
    }

    data class DifficultyData(val name: String, val length: Int)

    private val listData: List<DifficultyData> = arrayListOf(
        DifficultyData("Beginner",20),
        DifficultyData("Warm up",20),
        DifficultyData("Easy",20),
        DifficultyData("Endurance",30),
        DifficultyData("Strength",30),
        DifficultyData("Power",30),
        DifficultyData("Athlete",40),
        DifficultyData("Pro Athlete",40),
        DifficultyData("Superhuman",40),
        DifficultyData("Conqueror",40),
    )


    private var selection: DifficultyData = listData[0]

    private fun makeSpan(name: String): SpannableString {
        val span = SpannableString(name)

        if(selection.name != name){
            span.setSpan(
                ForegroundColorSpan(Color.GRAY),
                0,
                span.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return span
    }

    private fun renderList(view: View) {
        val listItems = listData.map { makeSpan(it.name) }

        val adapter = ArrayAdapter(
            this.context,
            android.R.layout.simple_list_item_1,
            listItems
        )
        view.mylist.adapter = adapter
        view.mylist.setOnItemClickListener { parent, view2, position, id ->
            selection = listData[position]
            view.difficulty.text = "Difficulty: ${selection.name}"
            view.length.text = "Length: ${selection.length}"
            renderList(view)
        }
    }

    private fun updateDetails(view: View) {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClimbFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClimbFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}