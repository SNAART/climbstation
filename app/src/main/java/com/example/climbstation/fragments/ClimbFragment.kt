package com.example.climbstation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.climbstation.R
import kotlinx.android.synthetic.main.fragment_climb.*
import kotlinx.android.synthetic.main.fragment_climb.view.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //renderList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_climb, container, false)
        // Inflate the layout for this fragment view.mylist.adapter = ArrayAdapter(this.context, android.R.layout.simple_list_item_1,listOf("car", "plane"))
        renderList(view)
        return view
    }


    data class DifficultyData(val name: String, val level: Int)


    private val listData: List<DifficultyData> = arrayListOf(
        DifficultyData("Easy",1),
        DifficultyData("Medium",2),
        DifficultyData("Hard",3),
    )


    private var selection: DifficultyData = listData[0]


    private fun renderList(view: View) {
        val listItems = listData.map { SpannableString(it.name) }

        val adapter = ArrayAdapter(
            this.context,
            android.R.layout.simple_list_item_1,
            listItems
        )
        view.mylist.adapter = adapter
        view.mylist.setOnItemClickListener { parent, view2, position, id ->
            selection = listData[position]
            view.difficulty.text = "Difficulty: ${selection.name}"
            view.length.text = "Length: ${selection.level}"
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