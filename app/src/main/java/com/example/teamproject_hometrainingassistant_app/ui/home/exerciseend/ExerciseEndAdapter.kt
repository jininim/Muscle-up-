package com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R

class ExerciseEndAdapter(private val itemList: ArrayList<String>):
    RecyclerView.Adapter<ExerciseEndAdapter.ViewHolder>(){

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            private val exerciseTextView : TextView = itemView.findViewById(R.id.exerciseText)

            fun bind(itemList: String){
                exerciseTextView.text = itemList
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_end,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = itemList[position]
        holder.bind(itemList)
    }
}