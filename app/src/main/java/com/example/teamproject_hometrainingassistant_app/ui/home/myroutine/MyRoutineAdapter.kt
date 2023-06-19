package com.example.teamproject_hometrainingassistant_app.ui.home.myroutine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseEndBinding

class MyRoutineAdapter(private val itemList: ArrayList<String>, private val timeList: ArrayList<String>):
    RecyclerView.Adapter<MyRoutineAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.exerciseText)
        val timeTextView: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myroutine_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = itemList[position]
        holder.timeTextView.text = timeList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}