package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend.ExerciseEndActivity
import com.example.teamproject_hometrainingassistant_app.ui.home.myroutine.MyRoutineAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class ExerciseStartActivity: AppCompatActivity() {
    private lateinit var binding: ActivityExerciseStartBinding

    private var currentExerciseIndex = 0
    private val exerciseList = ArrayList<String>()

    private var time = 0 // 타이머 시간 초기화
    private var timeTask : Timer? = null // 타이머 초기화

    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)
    val period = if (hour >= 12) "오후" else "오전"
    private val formattedTime = String.format("%s %d:%02d", period, hour % 12, minute)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseList.addAll(intent.getStringArrayListExtra("itemList")!!)

        setRecyclerView()

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }

        setExerciseName(exerciseList[currentExerciseIndex])
        binding.forwardButton.setOnClickListener {
            moveToNextExercise()
        }
        binding.backButton.setOnClickListener {
            moveToPreviousExercise()
        }
        binding.exerciseEndButton.setOnClickListener {
            finishExercise()
        }
        binding.setAddButton.setOnClickListener {

        }
    }

    private fun startTimer(){ // 타이머 작동 함수
        timeTask = timer(period = 10){
            time++

            val minutes = (time / 6000) % 60
            val seconds = (time / 100) % 60

            runOnUiThread {
                binding.timer.text = String.format("%02d:%02d", minutes, seconds)
            }
        }
    }

    private fun stopTimer(){
        timeTask?.cancel()
    }

    private fun moveToNextExercise(){ // 다음 화살표 누를 시 다음 운동으로
        if(currentExerciseIndex < exerciseList.size - 1){
            currentExerciseIndex++
            setExerciseName(exerciseList[currentExerciseIndex])
            binding.exerciseStartRecyclerView.adapter?.notifyDataSetChanged()
            updateArrowVisibility()
        }
    }

    private fun moveToPreviousExercise(){ // 이전 화살표 누를 시 이전 운동으로
        if (currentExerciseIndex > 0) {
            currentExerciseIndex--
            setExerciseName(exerciseList[currentExerciseIndex])
            binding.exerciseStartRecyclerView.adapter?.notifyDataSetChanged()
            updateArrowVisibility()
        }
    }
    private fun finishExercise() { // 운동 종료 시 다음 운동으로, 마지막일 시 운동 종료 화면으로 이동
        if (currentExerciseIndex < exerciseList.size - 1) {
            moveToNextExercise()
        } else {
            val currentTimer = binding.timer.text.toString() // 타이머 텍스트뷰
             // 시작 시간 넘기기
            val intent = Intent(this, ExerciseEndActivity::class.java)
            intent.putExtra("currentTimer", currentTimer)
            intent.putExtra("startTime", formattedTime)
            intent.putStringArrayListExtra("exerciseNames", exerciseList)
            startActivity(intent)
            finish()
        }
    }

    private fun setExerciseName(name : String){ // 현재 운동 이름 띄우기
        binding.exerciseStartName.text = name
    }

    private fun updateArrowVisibility(){ // 처음과 마지막운동일 시 각각 이전, 다음 화살표 비활성화
        binding.backButton.isEnabled = currentExerciseIndex != 0
        binding.forwardButton.isEnabled = currentExerciseIndex != exerciseList.size - 1
    }

    private fun setRecyclerView(){ // 시간을 못가져와서 임시로 넣어 놓은 리사이클러뷰..
        val setCount = 3

        val items = ArrayList<String>()
        for(i in 1 .. setCount){
            items.add("$i 세트")
        }
        val recyclerView = binding.exerciseStartRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ExerciseStartAdapter(items, binding)
        recyclerView.adapter = adapter

        binding.setAddButton.setOnClickListener {
            val newItem = if(items.isEmpty()){
                "1세트"
            } else {
                val lastSetNumber = items.last().split(" ")[0].toInt()
                "${lastSetNumber + 1} 세트"
            }

            items.add(newItem)
            adapter.notifyDataSetChanged()
        }
        binding.setSubButton.setOnClickListener {
            adapter.removeLastItem()
        }
    }

    override fun onStart() {
        super.onStart()
        startTimer() // 화면 시작 시 타이머 가동
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer() // 화면 종료 시 타이머 종료
    }
}