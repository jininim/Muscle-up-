package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemNoticeBoardBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail.NoticeBoardDetailActivity

class NoticeBoardAdapter(val onItemClicked: (NoticeBoardData) -> Unit) :
    ListAdapter<NoticeBoardData, NoticeBoardAdapter.ViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemNoticeBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemNoticeBoardBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: NoticeBoardData){
            binding.title.text = item.text
            binding.communityContent.text = item.content
            binding.communityUser.text = item.name

            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    companion object{
        val DiffUtil = object : DiffUtil.ItemCallback<NoticeBoardData>(){ // 기존의 데이터 리스트와 교체할 데이터 리스트를 비교해서 실질적으로 업데이트가 필요한 아이템들을 추려낸다.
            override fun areItemsTheSame(oldItem: NoticeBoardData, newItem: NoticeBoardData): Boolean {
                return oldItem == newItem
            }                             // 두 아이템이 동일한 아이템인지 체크한다. 예를들어 item 이 자신만의 고유한 id 같은걸 가지고 있다면 그걸 기준으로 삼으면 된다.

            override fun areContentsTheSame(oldItem: NoticeBoardData, newItem: NoticeBoardData): Boolean {
                return oldItem == newItem // 두 아이템이 동일한 내용물을 가지고 있는지 체크한다. 이 메서드는 areItemsTheSame()가 true 일 때만 호출된다.
            }

        }
    }
}