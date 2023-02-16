package com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseCategoryBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseCategoryData

class ExerciseCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<ExerciseCategoryAdapter.ViewHolder>() {

    var datas = mutableListOf<ExerciseCategoryData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemExerciseCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemExerciseCategoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ExerciseCategoryData){
            binding.categoryButton.text = item.text
        }
    }
}