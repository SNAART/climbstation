package com.example.climbstation

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sound.*


class SoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        val shred: SharedPreferences = getSharedPreferences("pref", MODE_PRIVATE)
        val bol = shred.getBoolean("sound", false)

        sound_switch.isChecked = bol

        sound_switch.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
           if (b){
               mute()
           }else
               unMute()
        }
    }


    private fun mute() {
        //mute audio
        val amanager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true)
       save(true)
    }

    fun unMute() {
        //unmute audio
        val amanager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false)
        save(false)
    }
    fun save(bol:Boolean){
        val spassword :SharedPreferences=getSharedPreferences("pref", MODE_PRIVATE)
        val editor:SharedPreferences.Editor=spassword.edit()
        editor.putBoolean("sound",bol)
        editor.apply()
    }
}