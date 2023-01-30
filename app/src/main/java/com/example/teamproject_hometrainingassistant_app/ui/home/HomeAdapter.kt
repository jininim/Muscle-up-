package com.example.teamproject_hometrainingassistant_app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemHomeBinding

class HomeAdapter(private val context: HomeFragment) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var datas = mutableListOf<HomeData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: HomeData){
            Glide.with(itemView).load(item.img).into(binding.homeRoutineImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.homeRoutineButton.text = item.text
            Glide.with(itemView).load(item.img2).into(binding.homeRoutineImageView2)
        }
    }
}