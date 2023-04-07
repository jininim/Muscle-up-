package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityCalendarRoutineBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityCalendarRoutineBinding
class CalendarRoutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalendarRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}