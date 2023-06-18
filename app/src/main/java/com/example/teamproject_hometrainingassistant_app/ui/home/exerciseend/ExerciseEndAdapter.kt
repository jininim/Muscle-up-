package com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseEndBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ItemExerciseStartBinding
import org.w3c.dom.Text

class ExerciseEndAdapter(private val itemList: ArrayList<String>, private val setList: ArrayList<String> , private val timeList: ArrayList<String>):
    RecyclerView.Adapter<ExerciseEndAdapter.ViewHolder>(){

        inner class ViewHolder(private val binding: ItemExerciseEndBinding) : RecyclerView.ViewHolder(binding.root){

            @SuppressLint("SetTextI18n")
            fun bind(itemList: String, setItem: String, timeItem: String){
                binding.exerciseText.text = itemList
                binding.time.text = "${setItem}세트 ${timeItem}회"
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemExerciseEndBinding.inflate(view, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = itemList[position]
        val setItem = setList[position]
        val timeItem = timeList[position]
        holder.bind(itemList, setItem, timeItem)
    }
}