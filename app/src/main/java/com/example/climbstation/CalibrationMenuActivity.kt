package com.example.climbstation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calibration_menu.*

class CalibrationMenuActivity : AppCompatActivity() {
    private var num :Int= 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calibration_menu)

        btn_plus.setOnClickListener{
            num++
            setText()

        }
        btn_minus.setOnClickListener{
            if(num>0){
                num--;
            }
            setText();
        }
    }

    fun setText() {
        txNumber.text = num.toString() + ""
    }
}