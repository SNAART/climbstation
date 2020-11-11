package com.example.climbstation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.climbstation.fragments.ClimbFragment
import com.example.climbstation.fragments.CreateFragment
import com.example.climbstation.fragments.SettingsFragment
import com.example.climbstation.fragments.StatisticsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_account.*
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
        sign_up.setOnClickListener { gotToSignUp()}
    }

    private fun gotToSignUp(){
        setContentView(R.layout.create_account)
        sign_up_2.setOnClickListener {
            startApp()
        }
    }
    private fun goToSignIn() {
        setContentView(R.layout.sign_in_form)
        sign_in_2.setOnClickListener {
            // TODO: login logic with firebase
            startApp()
        }
    }

    private fun useFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }

    private fun startApp(){
        setContentView(R.layout.activity_main)

        useFragment(climbFragment)
        /*button.setOnClickListener {
            val intent = Intent(this@MainActivity, QRScanActivity::class.java)
            // intent.putExtra("User", user);
            startActivity(intent)
        }*/

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

