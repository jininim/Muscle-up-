package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseOptionBinding

class ExerciseOptionAdapter(private val context: Context) :
    RecyclerView.Adapter<ExerciseOptionAdapter.ViewHolder>() {

    var datas = mutableListOf<ExerciseOptionData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemExerciseOptionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemExerciseOptionBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ExerciseOptionData){
            binding.exerciseOptionButton.text = item.text
        }
    }
}