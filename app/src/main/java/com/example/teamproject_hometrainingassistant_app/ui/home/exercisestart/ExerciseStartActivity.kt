package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding

class ExerciseStartActivity: AppCompatActivity() {
    private lateinit var binding: ActivityExerciseStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}