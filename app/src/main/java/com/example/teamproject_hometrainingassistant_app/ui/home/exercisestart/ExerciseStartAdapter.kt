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
    private var check = -1
    private var what = true

    inner class ViewHolder(val binding: ItemExerciseStartBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(exerciseTime: String, exerciseSet: String, position: Int, check1: Boolean){
            binding.exerciseSet.text = "${exerciseSet}세트"
            binding.exerciseTime.text = "${exerciseTime}회"
            binding.check1.isChecked = check1
            if (what){
                binding.check1.isChecked = position <= check
            }
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
        holder.bind(exerciseTimeText, exerciseSet, position, what)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setExerciseData(exerciseSet: String, exerciseTime: String, check: Boolean){
        exerciseSetCount = exerciseSet
        exerciseTimeText = exerciseTime
        what = check
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

    @SuppressLint("NotifyDataSetChanged")
    fun setCheck(i: Int, w: Boolean) {
        if (i in 0 until itemCount) {
            // i 값이 유효한 범위 내에 있을 때만 check 변수 설정
            check = i
            notifyDataSetChanged()
        }
        what = w
        notifyDataSetChanged()
    }
}