package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.R

class DashboardAdapter(private val context: DashboardFragment) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var datas = mutableListOf<DashboardRoutineData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val routineImg: ImageView = itemView.findViewById(R.id.routineImageView)
        private val routineText: TextView = itemView.findViewById(R.id.routineTextView)

        fun bind(item: DashboardRoutineData){
            Glide.with(itemView).load(item.img).into(routineImg) // 이미지 연결에 용이한 Glide 라이브러리 사용
            routineText.text = item.text
        }
    }
}