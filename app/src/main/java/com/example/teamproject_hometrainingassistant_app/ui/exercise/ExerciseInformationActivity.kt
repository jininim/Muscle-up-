package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseInformationBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExerciseInformationBinding
class ExerciseInformation : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}