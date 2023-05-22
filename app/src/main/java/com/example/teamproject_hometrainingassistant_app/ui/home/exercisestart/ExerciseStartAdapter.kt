package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding

class ExerciseStartAdapter(private val itemList: ArrayList<String>, private val binding: ActivityExerciseStartBinding):
    RecyclerView.Adapter<ExerciseStartAdapter.ViewHolder>(){

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            private val setTextView: TextView = itemView.findViewById(R.id.order)
            private val exerciseTimeTextView: TextView = itemView.findViewById(R.id.exerciseTime)
            val checkBox: CheckBox = itemView.findViewById(R.id.check)

            fun bind(setItem: String){
                setTextView.text = setItem
                exerciseTimeTextView.text = "10회"
                checkBox.isChecked = false
            }

            fun incrementLastSetCount() {
                // 마지막 아이템의 세트 수 증가
                if (itemCount > 0) {
                    val lastItem = itemList.last()
                    val setCount = lastItem.split(" ")[1].toInt()
                    val updatedSetCount = setCount + 1
                    val updatedItem = "${setCount + 1}세트"
                    itemList[itemList.size - 1] = updatedItem
                    notifyItemChanged(itemCount - 1)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_start, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setItem = itemList[position]
        holder.bind(setItem)
    }

    private fun isAllItemsChecked(): Boolean{
        for(item in itemList){
            val position = itemList.indexOf(item)
            val viewHolder = binding.exerciseStartRecyclerView.findViewHolderForAdapterPosition(position) as? ViewHolder
            if (viewHolder != null && !viewHolder.checkBox.isChecked){
                return false
            }
        }
        return true
    }

    fun addItem() {
        // 새로운 아이템 추가
        val itemCount = itemList.size
        val newItem = "${itemList.size + 1} 세트"
        itemList.add(newItem)
        notifyItemInserted(itemCount)
    }

    fun removeLastItem() {
        // 마지막 아이템 삭제
        if (itemList.isNotEmpty()) {
            itemList.removeAt(itemList.size - 1)
            notifyItemRemoved(itemList.size)
        }
    }




}