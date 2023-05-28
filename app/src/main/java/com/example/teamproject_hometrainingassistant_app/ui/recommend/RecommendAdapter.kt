package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ItemRecommendBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseModel

class RecommendAdapter(private val context: Context, private val videoList: ArrayList<String>, private val videoTitleList: ArrayList<String>) :
    RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    var datas = mutableListOf<RecommendData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoUrl = videoList[position]
        val videoTitle = videoTitleList[position]
        holder.bind(videoTitle)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RoutineDetailActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            intent.putExtra("videoTitle", videoTitle)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val videoTitleText: TextView = itemView.findViewById(R.id.recommendButton)
        fun bind(videoTitle : String){
            videoTitleText.text = videoTitle
        }
    }
}