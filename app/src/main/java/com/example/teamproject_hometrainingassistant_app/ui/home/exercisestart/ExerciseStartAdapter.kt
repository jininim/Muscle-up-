package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R

class ExerciseStartAdapter(private val itemList: ArrayList<String>):
    RecyclerView.Adapter<ExerciseStartAdapter.ViewHolder>(){

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val routineTextView: TextView = itemView.findViewById(R.id.exerciseName)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_start, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.routineTextView.text = itemList[position]
    }
}