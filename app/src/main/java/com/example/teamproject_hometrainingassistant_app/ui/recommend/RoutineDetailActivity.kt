package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRoutineDetailsBinding
//import com.google.android.exoplayer2.SimpleExoPlayer
//import com.google.android.exoplayer2.source.ProgressiveMediaSource
//import com.google.android.exoplayer2.ui.PlayerView
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
//import com.google.android.exoplayer2.util.Util

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRoutineDetailsBinding
class RoutineDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUrl = intent.getStringExtra("videoUrl")
        val videoTitle = intent.getStringExtra("videoTitle")

        binding.routineTitle.text = videoTitle
        binding.routineTitleTextView.text = videoTitle

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}