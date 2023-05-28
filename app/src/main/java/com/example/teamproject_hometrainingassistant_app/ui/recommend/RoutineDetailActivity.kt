package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRoutineDetailsBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseActivity
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartActivity

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRoutineDetailsBinding
class RoutineDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoTitle = intent.getStringExtra("videoTitle")

        binding.routineTitle.text = videoTitle
        binding.routineTitleTextView.text = videoTitle

        binding.routineStartButton.setOnClickListener {
            val intent = Intent(this, ExerciseStartActivity::class.java)
            startActivity(intent)
        }

        binding.routineAddButton.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}