package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemDashboardBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseInformation

class DashboardAdapter(private val context: DashboardFragment) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var datas = mutableListOf<DashboardRoutineData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemDashboardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemDashboardBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context

        fun bind(item: DashboardRoutineData){
            Glide.with(itemView).load(item.img).into(binding.routineImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.routineTextView.text = item.text

            binding.routineTextView.setOnClickListener {
                val intent = Intent(context, CalendarRoutineActivity::class.java)
                intent.run { context.startActivity(this) }
            }
        }
    }
}