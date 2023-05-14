package com.example.teamproject_hometrainingassistant_app.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.example.teamproject_hometrainingassistant_app.R

import com.example.teamproject_hometrainingassistant_app.ui.home.db.Routine

class HomeAdapter(val onClickUpdate: (routine: Routine) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var itemList = emptyList<Routine>()

    inner class HomeViewHolder(itemView: View) : ViewHolder(itemView) {
        val routineList: TextView = itemView.findViewById(R.id.routine)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(routine: List<Routine>) {
        this.itemList = routine
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home,parent,false)
        return HomeViewHolder(itemView)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val current = itemList[position]
        holder.routineList.text = current.routine
        holder.checkBox.setOnClickListener {
            if (!current.check) {
                current.check = true
                onClickUpdate.invoke(current)
            } else {
                current.check = false
                onClickUpdate.invoke(current)
            }
        }

    }
}