package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRoutineDetailsBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartActivity

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRoutineDetailsBinding
class RoutineDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.routineStartButton.setOnClickListener {
            val intent = Intent(this, ExerciseStartActivity::class.java)
            intent.run { startActivity(intent) }
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}