package com.example.climbstation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StatisticsAdapter(context: Context, private val stats: MutableList<Stats>): BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return stats.size
    }

    override fun getItem(position: Int): Any {
        return stats[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.stats_list_item, parent, false)

        val thisStat = stats[position]

        var tv = rowView.findViewById(R.id.tv_stat_name) as TextView
        tv.text = thisStat.name

/*

*/
        return rowView
    }
}
