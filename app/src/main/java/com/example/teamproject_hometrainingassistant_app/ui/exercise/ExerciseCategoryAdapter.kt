package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ExerciseCategoryItemBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ExersiceSearchItemBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityFragment

class ExerciseCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<ExerciseCategoryAdapter.ViewHolder>() {

    var datas = mutableListOf<ExerciseCategoryData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ExerciseCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ExerciseCategoryItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ExerciseCategoryData){
            binding.categoryButton.text = item.text
        }
    }
}