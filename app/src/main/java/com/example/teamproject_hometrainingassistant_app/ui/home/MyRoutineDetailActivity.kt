package com.example.teamproject_hometrainingassistant_app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.DecibelActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMyRoutineDetailBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.CreateNoticeBoardActivity
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartActivity

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMyRoutineDetailBinding
class MyRoutineDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRoutineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.decibelStartButton.setOnClickListener {
            val intent = Intent(this, DecibelActivity::class.java)
            intent.run { startActivity(this) }
        }

        binding.myRoutineStartButton.setOnClickListener {
            val intent = Intent(this,ExerciseStartActivity::class.java)
            intent.run { startActivity(intent) }
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}