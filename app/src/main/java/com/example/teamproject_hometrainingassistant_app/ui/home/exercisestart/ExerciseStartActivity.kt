package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.myroutine.MyRoutineAdapter

class ExerciseStartActivity: AppCompatActivity() {
    private lateinit var binding: ActivityExerciseStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemList = intent.getStringArrayListExtra("itemList")

        val recyclerView: RecyclerView = binding.exerciseStartRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ExerciseStartAdapter(itemList!!)
        recyclerView.adapter = adapter

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}