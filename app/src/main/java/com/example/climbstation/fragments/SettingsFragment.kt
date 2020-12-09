package com.example.climbstation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climbstation.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var username = FirebaseAuth.getInstance().currentUser?.email
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        view.sound.setOnClickListener{
            val intent:Intent= Intent(context,SoundActivity::class.java)
            startActivity(intent)
        }
        view.calibration_menu.setOnClickListener{
            val intent:Intent= Intent(context,CalibrationMenuActivity::class.java)
            startActivity(intent)
        }
        view.btn_Owner_menu.setOnClickListener{
            val intent:Intent= Intent(context,OwnersMenuActivity::class.java)
            startActivity(intent)
        }

        view.btn_security.setOnClickListener{
            val intent:Intent= Intent(context,SecurityActivity::class.java)
            startActivity(intent)
        }
        view.terrains.setOnClickListener{
            val intent:Intent= Intent(context,EditTerrins::class.java)
            startActivity(intent)
        }

//        view.tv_name.text = username
//
        return view
        // Inflate the layout for this fragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance( param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param2)
//                    tv_name.text = username

                }
            }
    }
}