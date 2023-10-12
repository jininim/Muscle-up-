package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ItemChatBinding
import java.text.SimpleDateFormat

class NoticeBoardDetailAdapter : ListAdapter<NoticeBoardDetailData, NoticeBoardDetailAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(chatItem: NoticeBoardDetailData){
            val postDate = SimpleDateFormat("MM/dd")
            val postTime = SimpleDateFormat("hh:mm")

            binding.chatName.text = chatItem.name
            binding.chatContent.text = chatItem.message
            binding.chatDate.text = postDate.format(chatItem.time)
            binding.chatTime.text = postTime.format(chatItem.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<NoticeBoardDetailData>(){
            override fun areItemsTheSame(oldItem: NoticeBoardDetailData, newItem: NoticeBoardDetailData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NoticeBoardDetailData, newItem: NoticeBoardDetailData): Boolean {
                return oldItem == newItem
            }

        }
    }
}