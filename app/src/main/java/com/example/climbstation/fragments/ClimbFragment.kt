package com.example.climbstation.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
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
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.example.climbstation.ConnectionInfo
import com.example.climbstation.FirebaseRepo
import com.example.climbstation.R
import com.example.climbstation.retrofit.RestApiService
import kotlinx.android.synthetic.main.activity_owners_menu.*
import kotlinx.android.synthetic.main.fragment_climb.*
import kotlinx.android.synthetic.main.fragment_climb.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.min
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
    private var restApiService = RestApiService()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var key: String? = null
    private var started = false
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var phaseTimer: CountDownTimer
    private val initialCountDown: Long = 6000000
    private val countDownInterval: Long = 1000
    private var millisecondsPassed: Long = 0
    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private var animation: ObjectAnimator? = null
    private var climbingPhase: Int = 0

    private var speedMMperSecond: Int = 200
    private fun calculateTime(MMspeed: Int, distance: Int)  : Int{
        // time = disance / speed
        val mm = distance * 1000
        var time = mm / MMspeed
        Log.d("asd", "Calculate : MMspeedpersec ${MMspeed}, distance: ${mm}, result ${time}")
        return time
    }

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
                // Log.d("asd", "onTick timeLeft: ${timeLeft}")
                // Log.d("asd", "millisecondsPassed: ${millisecondsPassed}")
            }

            override fun onFinish() {
                Log.d("asd", "Timer has finished.")
            }
        }
    }

    private fun setPhaseTimer(view: View) {
        val distance = selection.climbingPhases[climbingPhase].distance
        val phaseTime: Long = calculateTime(speedMMperSecond, distance).toLong()

        Log.d("asd", "Setting phase timer ${climbingPhase} time: ${phaseTime} seconds")

        // set angle
        val angle = selection.climbingPhases[climbingPhase].angle.toString()
        setAngle(angle)

        // set the progress for this phase
        val barId = "bar${climbingPhase + 1}"
        val id: Int = resources.getIdentifier(barId, "id", "com.example.climbstation")
        val progress = view.findViewById<ProgressBar>(id)
        progress.max = 100
        animation = ObjectAnimator.ofInt(progress, "progress", 100)
            .setDuration(phaseTime*1000)
        animation?.start()

        // start timer for end of phase
        phaseTimer = object : CountDownTimer(phaseTime * 1000, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                val phases = selection.climbingPhases;
                // IS THE CURRENT INDEX THE LAST IN THE LIST?? index 2  == size 3
                val lastIndex = phases.size - 1;
                val currentIndex = climbingPhase;
                if (currentIndex == lastIndex) {
                    // stop the run
                    Log.d("asd", "Phase timer finished, stopping climb")
                    operate(view)
                } else {
                    //start the next phase
                    Log.d("asd", "Phase timer finished, starting next phase")
                    climbingPhase = climbingPhase + 1
                    setPhaseTimer(view)

                }
            }
        }
        phaseTimer.start()
    }

    private fun setAngle(angle: String) {
        Log.d("asd", "Setting wall angle ${angle}")
        val angleInfo = ConnectionInfo(
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
            null,
            null,
            angle
        )
        restApiService.setAngle(angleInfo) {
            if (it?.response == "OK") {
                Log.d("asd", "Angle set successfully")
            } else {
                Log.d("asd", "Angle was not set successfully")
            }
        }
    }

    private fun setSpeed(speed: String) {
        Log.d("asd", "Setting wall speed ${speed}")
        val speedInfo = ConnectionInfo(
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
            null,
            speed,
            null
        )
        restApiService.setSpeed(speedInfo) {
            if (it?.response == "OK") {
                Log.d("asd", "Speed set successfully")
            } else {
                Log.d("asd", "Speed was not set successfully")
            }
        }
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

        view.difficulty.text = "Difficulty: ${selection.name}"
        view.length.text = "Length: ${selection.length}"

        // trackProgress(view)

        return view
    }

    fun clearProgress(view: View) {
        for (i in 1..10) {
            val barId = "bar${i}"
            val id: Int = resources.getIdentifier(barId, "id", "com.example.climbstation")
            val progress = view.findViewById<ProgressBar>(id)
            progress.progress = 0
            progress.max = 100
        }
    }

    fun login() {
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
            null,
            null
        )

        restApiService.login(userInfo) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun operate(view: View){
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
            null,
            null
        )

        restApiService.operate(userInfo){
            if (it != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
                Log.d("asd", "start response ${it.response}")

                // the "|| true" part below is to make testing easier, maybe remove when server always works
                if(it.response == "OK" || true){
                    started = !started
                    if (started) {
                        clearProgress(view)
                        setSpeed(speedMMperSecond.toString())
                        countDownTimer.start()
                        view.start_button.text = "Stop"
                        climbingPhase = 0
                        setPhaseTimer(view)
                    } else {

                        view.start_button.text = "Start"
                        countDownTimer.cancel()
                        phaseTimer.cancel()
                        Log.d("asd", "Total climb time: ${millisecondsPassed / 1000} seconds")
                        //calculate time duration and send to statistics
                        var email = firebaseRepo.getUser()
                        var climbTime = millisecondsPassed / 1000
                        var climbLength: Double = (climbTime.toDouble() * speedMMperSecond.toDouble()) / 1000
                        Log.d("asd", "climb Length is: ${climbLength} meters")
                        if (email != null) {
                            firebaseRepo.sendData(email, climbTime, selection.name, climbLength.toInt(), 8,selection.length)
                        }

                        // set progress to be where run was stopped
                        val completedPhasesLength = selection
                            .climbingPhases
                            .filterIndexed { index, it -> index < climbingPhase }
                            .sumBy { it.distance }
                        val currentPhaseLength = selection.climbingPhases[climbingPhase].distance
                        val currentPhaseProgress = climbLength - completedPhasesLength
                        val currentPhasePercentage = currentPhaseProgress / currentPhaseLength * 100
                        animation?.end()
                        val barId = "bar${climbingPhase + 1}"
                        val id: Int = resources.getIdentifier(barId, "id", "com.example.climbstation")
                        val progress = view.findViewById<ProgressBar>(id)
                        progress.progress = currentPhasePercentage.toInt()
                        Log.d("asd", "Setting final progress ${currentPhasePercentage}")

                        //do the action related to the selected mode
                        modeAction(view)

                        millisecondsPassed = 0
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

    private fun modeAction(view: View){
        if(selectedMode == "To next level"){
            var currentID = listData.indexOf(selection)
            val nextID: Int = min(currentID + 1, listData.size - 1)
            selection = listData.get(nextID)
            renderList(view)
        } else if (selectedMode == "Random") {
            val maxValue = listData.size - 1
            val nextID: Int = (0..maxValue).random()
            selection = listData.get(nextID)
            renderList(view)
        } else if (selectedMode == "Slow down") {
            speedMMperSecond -= 20
        }
    }

    data class AngleChange(val distance: Int, val angle: Int)
    data class DifficultyData(
        val name: String,
        val length: Int,
        val climbingPhases: List<AngleChange>)

    private val listData: List<DifficultyData> = arrayListOf(
        DifficultyData(
            "Beginner",
            20,
            listOf(
                AngleChange(2, 15),
                AngleChange(2, 15),
                AngleChange(2, 15),
                AngleChange(2, 12),
                AngleChange(2, 10),
                AngleChange(2, 10),
                AngleChange(2, 10),
                AngleChange(2, 5),
                AngleChange(2, 15),
                AngleChange(2, 15),
            )
        ),
        DifficultyData(
            "Warm up",
            20,
            listOf(
                AngleChange(2, 15),
                AngleChange(2, 5),
                AngleChange(2, 10),
                AngleChange(2, 5),
                AngleChange(2, 12),
                AngleChange(2, 10),
                AngleChange(2, 15),
                AngleChange(2, 0),
                AngleChange(2, 5),
                AngleChange(2, 15),
            )),
        DifficultyData(
            "Easy",
            20,
            listOf(
                AngleChange(2, 15),
                AngleChange(2, 0),
                AngleChange(2, 5),
                AngleChange(2, 0),
                AngleChange(2, 5),
                AngleChange(2, 12),
                AngleChange(2, -3),
                AngleChange(2, 3),
                AngleChange(2, 10),
                AngleChange(2, 12),

            )),
        DifficultyData(
            "Endurance",
            30,
            listOf(
                AngleChange(3, 10),
                AngleChange(3, -4),
                AngleChange(3, 0),
                AngleChange(3, 3),
                AngleChange(6, -5),
                AngleChange(3, 5),
                AngleChange(3, -10),
                AngleChange(3, 2),
                AngleChange(3, 10),
            )),
        DifficultyData(
            "Strength",
            30,
            listOf(
                AngleChange(3, 10),
                AngleChange(3, -6),
                AngleChange(3, -2),
                AngleChange(3, 5),
                AngleChange(3, -8),
                AngleChange(3, 0),
                AngleChange(3, -10),
                AngleChange(3, 8),
                AngleChange(3, -15),
                AngleChange(3, 5),
            )),
        DifficultyData(
            "Power",
            30,
            listOf(
                AngleChange(3, 5),
                AngleChange(3, -10),
                AngleChange(3, -5),
                AngleChange(3, 10),
                AngleChange(3, 8),
                AngleChange(3, -15),
                AngleChange(3, 7),
                AngleChange(3, -15),
                AngleChange(3, 4),
                AngleChange(3, 10),
                AngleChange(3, -18),
                AngleChange(3, -5),
            )),
        DifficultyData(
            "Athlete",
            40,
            listOf(
                AngleChange(4, 5),
                AngleChange(4, -15),
                AngleChange(4, -10),
                AngleChange(4, 5),
                AngleChange(4, -12),
                AngleChange(4, -20),
                AngleChange(4, 3),
                AngleChange(4, -25),
                AngleChange(4, -5),
                AngleChange(4, -10),
            )),
        DifficultyData(
            "Pro Athlete",
            40,
            listOf(
                AngleChange(4, 5),
                AngleChange(4, -22),
                AngleChange(4, 7),
                AngleChange(4, -30),
                AngleChange(4, 5),
                AngleChange(4, -33),
                AngleChange(4, 8),
                AngleChange(4, -22),
                AngleChange(4, 0),
                AngleChange(4, -25),
            )),
        DifficultyData(
            "Superhuman",
            40,
            listOf(
                AngleChange(4, 0),
                AngleChange(4, -40),
                AngleChange(4, 5),
                AngleChange(4, -35),
                AngleChange(4, 8),
                AngleChange(4, -42),
                AngleChange(4, -15),
                AngleChange(4, 3),
                AngleChange(4, -35),
                AngleChange(4, -40),
            )),
        DifficultyData(
            "Conqueror",
            40,
            listOf(
                AngleChange(4, -5),
                AngleChange(4, -45),
                AngleChange(4, 8),
                AngleChange(4, -40),
                AngleChange(4, 5),
                AngleChange(4, -43),
                AngleChange(4, -20),
                AngleChange(4, -10),
                AngleChange(4, -38),
                AngleChange(4, 10),
            )),
    )


    private var selection: DifficultyData = listData[0]

    private fun makeSpan(name: String): SpannableString {
        val span = SpannableString(name)

        if(selection.name == name){
            span.setSpan(
                ForegroundColorSpan(Color.RED),
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
        view.difficulty.text = "Difficulty: ${selection.name}"
        view.length.text = "Length: ${selection.length}"
        view.mylist.adapter = adapter
        view.mylist.setOnItemClickListener { parent, view2, position, id ->
            selection = listData[position]
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