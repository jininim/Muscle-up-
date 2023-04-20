package com.example.teamproject_hometrainingassistant_app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemHomeBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.db.Routine

class HomeAdapter() :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var itemList = emptyList<Routine>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: Routine){
            binding.textView.text = item.routine
            binding.checkBox.isChecked = item.check
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(routine: List<Routine>) {
        this.itemList = routine
        notifyDataSetChanged()
    }
}