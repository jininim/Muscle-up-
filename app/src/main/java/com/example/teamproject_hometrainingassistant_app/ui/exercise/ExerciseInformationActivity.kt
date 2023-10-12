package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseInformationBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExerciseInformationBinding
class ExerciseInformation : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val exerciseUrl = intent.getStringExtra("exerciseUrl")
        val guide = intent.getStringExtra("guide")
        val youtubeUrl = intent.getStringExtra("youtubeUrl")

        binding.name.text = name
        binding.url.apply {
            Glide.with(binding.url)
                .load(exerciseUrl)
                .into(binding.url)
        }
        binding.guide.text = guide

        //뒤로가기 버튼 클릭 시
        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

}