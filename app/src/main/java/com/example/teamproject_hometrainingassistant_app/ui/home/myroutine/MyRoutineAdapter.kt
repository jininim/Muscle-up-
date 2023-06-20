package com.example.teamproject_hometrainingassistant_app.ui.home.myroutine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseEndBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseStartBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ItemMyroutineDetailBinding

class MyRoutineAdapter(
    private val itemList: ArrayList<String>,
    private val timeList: ArrayList<String>,
    private val onItemClick: (String, Int) -> Unit
) :
    RecyclerView.Adapter<MyRoutineAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemMyroutineDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val settingButton: ImageView = binding.settingButton
        fun bind(exercise: String, count: String, position: Int){
            binding.exerciseText.text = exercise
            binding.time.text = count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemMyroutineDetailBinding.inflate(view, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseText = itemList[position]
        val countText = timeList[position]
        holder.bind(exerciseText, countText, position)
        holder.settingButton.setOnClickListener {
            onItemClick(timeList[position], position) // 클릭 이벤트 발생 시 람다 식 실행
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setCountUpdate(editCountSet: String, position: Int) {
        timeList[position] = editCountSet
        notifyItemChanged(position)
    }
}