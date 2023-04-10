package com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExersiceSearchBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardData
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseModel

class ExerciseSearchAdapter(val onItemClicked: (ExerciseModel) -> Unit) :
    ListAdapter<ExerciseModel, ExerciseSearchAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemExersiceSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemExersiceSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ExerciseModel){
            Glide.with(itemView).load(item.exerciseUrl).into(binding.exerciseImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.exerciseButton.text = item.name

            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<ExerciseModel>(){
            override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}