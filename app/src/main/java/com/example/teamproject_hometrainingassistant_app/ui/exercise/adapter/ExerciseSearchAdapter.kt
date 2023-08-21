package com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExersiceSearchBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardData
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail.NoticeBoardDetailAdapter.Companion.diffUtil
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseModel

class ExerciseSearchAdapter(val onItemClicked: (ExerciseModel) -> Unit,val onCheckBoxClick: (ArrayList<String>, ArrayList<String>, ArrayList<String>) -> Unit) :
    ListAdapter<ExerciseModel, ExerciseSearchAdapter.ViewHolder>(diffUtil) {
    val nameList : ArrayList<String> = ArrayList()
    val timeList : ArrayList<String> = ArrayList()
    val urlList : ArrayList<String> = ArrayList()

    private var currentList: List<ExerciseModel> = emptyList()
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
            binding.exerciseButton.setOnClickListener {
                onItemClicked(item)
            }

            binding.exerciseButton.text = item.name

            //체크박스 클릭 시
            binding.exerciseCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    //체크된 항목의 item.name을 nameList에 저장
                    nameList.add(item.name)
                    timeList.add(item.times)
                    urlList.add(item.exersiceUrl)
                    onCheckBoxClick(nameList, timeList, urlList)
                }
            }
        }
    }
    override fun submitList(list: List<ExerciseModel>?) {
        currentList = list ?: emptyList() // Update the current list
        super.submitList(list)
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