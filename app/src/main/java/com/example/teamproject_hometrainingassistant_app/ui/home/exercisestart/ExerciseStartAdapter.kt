package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseStartBinding

class ExerciseStartAdapter: RecyclerView.Adapter<ExerciseStartAdapter.ViewHolder>(){

    private var exerciseSetCount = ""
    private var exerciseTimeText = ""

    inner class ViewHolder(private val binding: ItemExerciseStartBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(exerciseTime: String, exerciseSet: String){
            binding.exerciseSet.text = "${exerciseSet}세트"
            binding.exerciseTime.text = "${exerciseTime}회"
            binding.check.isChecked = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemExerciseStartBinding.inflate(view, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exerciseSetCount.toInt()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseSet = (position + 1).toString()
        holder.bind(exerciseTimeText, exerciseSet)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setExerciseData(exerciseSet: String, exerciseTime: String){
        exerciseSetCount = exerciseSet
        exerciseTimeText = exerciseTime
        notifyDataSetChanged()
    }

    fun addExerciseSet() {
        val newSetCount = exerciseSetCount.toInt() + 1
        exerciseSetCount = newSetCount.toString()
        notifyItemInserted(newSetCount - 1)
    }

    fun removeLastExerciseSet() {
        if (exerciseSetCount.toInt() > 0) {
            val newSetCount = exerciseSetCount.toInt() - 1
            exerciseSetCount = newSetCount.toString()
            notifyItemRemoved(newSetCount)
        }
    }
}