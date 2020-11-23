package com.example.climbstation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.statistics_list.*

class StatisticsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics_list)


        val statsAdapter = StatisticsAdapter(this,GlobalModel.stats)

        lv_stats.adapter = statsAdapter
    }
}