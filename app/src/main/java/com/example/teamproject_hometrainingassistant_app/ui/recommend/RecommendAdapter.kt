package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemRecommendBinding

class RecommendAdapter(private val context: Context) :
    RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    var datas = mutableListOf<RecommendData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: RecommendData){
            Glide.with(itemView).load(item.img).into(binding.recommendImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.recommendButton.text = item.text

            binding.recommendButton.setOnClickListener {
                val intent = Intent(context, RoutineDetailActivity::class.java)
                intent.run { context.startActivity(this) }
            }
        }
    }
}