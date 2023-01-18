package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.DashboardItemBinding

class DashboardAdapter(private val context: DashboardFragment) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var datas = mutableListOf<DashboardRoutineData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = DashboardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: DashboardItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DashboardRoutineData){
            Glide.with(itemView).load(item.img).into(binding.routineImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.routineTextView.text = item.text
        }
    }
}