package com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseEndBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExerciseEndActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExerciseEndBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MutatingSharedPrefs")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val currentDate = getCurrentDate() // 현재 시간 가져오기
        var dayCount = sharedPreferences.getInt("DayCount", 0) // 몇일차인지 카운트
        val savedDate = sharedPreferences.getString("CurrentDate", null) // 최근 날짜 저장

        val itemList = intent.getStringArrayListExtra("exerciseNames") // 운동 이름 리스트
        val currentTimer = intent.getStringExtra("currentTimer") // 운동한 총 시간
        val startTime = intent.getStringExtra("startTime") // 운동 시작 시간

        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val period = if (hour >= 12) "오후" else "오전"
        val formattedTime = String.format("%s %d:%02d", period, hour % 12, minute) // 운동 종료 시간

        // 현재 날짜와 저장된 날짜 비교
        if (savedDate != currentDate) {
            // 저장된 날짜와 현재 날짜가 다를 경우, 일 수 증가
            dayCount++
            sharedPreferences.edit()
                .putString("CurrentDate", currentDate)
                .putInt("DayCount", dayCount)
                .apply()
        }

        // 일 수를 화면에 표시
        binding.routineTotalDate.text = "${dayCount}일차"
        binding.routineTotalTime.text = "${currentTimer}"
        binding.startTime.text = "${startTime}"
        binding.endTime.text = "${formattedTime}"

        val recyclerView: RecyclerView = binding.routineEndRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ExerciseEndAdapter(itemList!!)
        recyclerView.adapter = adapter

        binding.routineEndSaveButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    private fun getCurrentDate(): String { // yyyy-MM-dd 형식의 현재 날짜를 저장.
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

}