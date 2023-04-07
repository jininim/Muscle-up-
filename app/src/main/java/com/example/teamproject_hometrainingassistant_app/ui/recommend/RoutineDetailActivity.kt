package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRoutineDetailsBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRoutineDetailsBinding
class RoutineDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}