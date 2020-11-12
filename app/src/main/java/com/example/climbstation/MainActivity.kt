package com.example.climbstation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.climbstation.fragments.ClimbFragment
import com.example.climbstation.fragments.CreateFragment
import com.example.climbstation.fragments.SettingsFragment
import com.example.climbstation.fragments.StatisticsFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.sign_in_form.*

class MainActivity : AppCompatActivity() {

    private val climbFragment =ClimbFragment()
    private val createFragment = CreateFragment()
    private val statisticsFragment = StatisticsFragment()
    private val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        sign_in.setOnClickListener { goToSignIn() }
    }

    private fun goToSignIn() {
        setContentView(R.layout.sign_in_form)
        sign_in_2.setOnClickListener {
            // TODO: login logic with firebase
            startQr()
        }
    }

    private fun useFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
    private fun  startQr(){
        val intent = Intent(this@MainActivity, QRScanActivity::class.java)
        startActivity(intent)
    }

    private fun startApp(){
        setContentView(R.layout.activity_main)

        useFragment(climbFragment)


        bottom_navigation_menu.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_climb -> useFragment(climbFragment)
                R.id.ic_create -> useFragment(createFragment)
                R.id.ic_statistics -> useFragment(statisticsFragment)
                R.id.ic_settings -> useFragment(settingsFragment)

            }
            true
        }
    }

}

