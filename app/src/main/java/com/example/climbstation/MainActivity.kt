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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_firstinit.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_climb.*
import kotlinx.android.synthetic.main.fragment_settings.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val climbFragment = ClimbFragment()
    private val createFragment = CreateFragment()
    private val statisticsFragment = StatisticsFragment()
    private val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            // User is signed in
            val intent = Intent (this, QRScanActivity::class.java )
            startActivity(intent)
        } else {
            // No user is signed in
            setContentView(R.layout.activity_firstinit)
            sign_in.setOnClickListener {
                val intent = Intent (this, LoginActivity::class.java )
                startActivity(intent)
            }
            sign_up.setOnClickListener {
                val intent = Intent (this, SignupActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun useFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }

    private fun startApp(){
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

