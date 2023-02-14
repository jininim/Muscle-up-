package com.example.teamproject_hometrainingassistant_app.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMyRoutineDetailBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMyRoutineDetailBinding
class MyRoutineDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRoutineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}