package com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseStartBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend.ExerciseEndActivity
import com.example.teamproject_hometrainingassistant_app.ui.home.myroutine.MyRoutineAdapter
import kotlinx.coroutines.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.math.log10

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class ExerciseStartActivity : AppCompatActivity() {

    private var fileName: String = ""

    private var recorder: MediaRecorder? = null
    private var isRecording = false
    private var job: Job? = null


    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private lateinit var binding: ActivityExerciseStartBinding

    private var currentExerciseIndex = 0
    private val exerciseList = ArrayList<String>()

    private var time = 0 // 타이머 시간 초기화
    private var timeTask: Timer? = null // 타이머 초기화

    private val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)
    val period = if (hour >= 12) "오후" else "오전"
    private val formattedTime = String.format("%s %d:%02d", period, hour % 12, minute)



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(
            this, permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        binding = ActivityExerciseStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseList.addAll(intent.getStringArrayListExtra("itemList")!!)

        //데시벨 받아오기
        val decibel = intent.getStringExtra("decibel")
        var check = false


        binding.decibelOnOffButton.setOnClickListener {
            check = !check
            if(check){
                startRecording() // 녹음 시작
                if (decibel != null) {
                    getDb(decibel = decibel.toInt())
                } //데시벨 측정
            }else{
                stopRecording()
                job?.cancel()
            }

        }
        val setCount = 3
        val items: MutableList<String> = mutableListOf()
        for (i in 1..setCount) {
            items.add("$i 세트")
        }
        val recyclerView = binding.exerciseStartRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ExerciseStartAdapter(items as ArrayList<String>, binding)
        recyclerView.adapter = adapter

        binding.addbutton.setOnClickListener {
            val lastSetNumber = items.last().split(" ")[0].toInt()
            val newItem = "${lastSetNumber + 1} 세트"
            items.add(newItem)
            adapter.notifyDataSetChanged()
        }

        binding.setSubButton.setOnClickListener {
            adapter.removeLastItem()
        }

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

    }

    private fun startTimer() { // 타이머 작동 함수
        timeTask = timer(period = 10) {
            time++

            val minutes = (time / 6000) % 60
            val seconds = (time / 100) % 60

            runOnUiThread {
                binding.timer.text = String.format("%02d:%02d", minutes, seconds)
            }
        }
    }

    private fun stopTimer() {
        timeTask?.cancel()
    }

    private fun moveToNextExercise() { // 다음 화살표 누를 시 다음 운동으로
        if (currentExerciseIndex < exerciseList.size - 1) {
            currentExerciseIndex++
            setExerciseName(exerciseList[currentExerciseIndex])
            binding.exerciseStartRecyclerView.adapter?.notifyDataSetChanged()
            updateArrowVisibility()
        }
    }

    private fun moveToPreviousExercise() { // 이전 화살표 누를 시 이전 운동으로
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

    private fun setExerciseName(name: String) { // 현재 운동 이름 띄우기
        binding.exerciseStartName.text = name
    }

    private fun updateArrowVisibility() { // 처음과 마지막운동일 시 각각 이전, 다음 화살표 비활성화
        binding.backButton.isEnabled = currentExerciseIndex != 0
        binding.forwardButton.isEnabled = currentExerciseIndex != exerciseList.size - 1
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() { // 시간을 못가져와서 임시로 넣어 놓은 리사이클러뷰..

    }

    override fun onStart() {
        super.onStart()
        startTimer() // 화면 시작 시 타이머 가동
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer() // 화면 종료 시 타이머 종료
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)//외부에서 들어오는 소리를 녹음
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)// 출력 파일 포맷을 설정
            setOutputFile(fileName) // 출력파일 이름 설정
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)// 오디오 인코더를 설정
            try {
                prepare() // 초기화
            } catch (e: IOException) {
                Log.e(PackageManagerCompat.LOG_TAG, "prepare() failed") // 실패시
            }
            Toast.makeText(this@ExerciseStartActivity, "자동 세기 기능 시작", Toast.LENGTH_LONG)
                .show()
            start() // 녹음 시작
        }
    }

    private fun stopRecording() {
        isRecording = false
        job?.cancel()
        recorder?.apply {
            stop() //녹음 중지
            release()
        }
        Toast.makeText(this, "자동 세기 기능 중지", Toast.LENGTH_LONG).show()
        recorder = null
    }
    @SuppressLint("SetTextI18n")
    private fun getDb(decibel : Int){
        var count1 = 0
        recorder?.let {
            isRecording = true
            // 녹음이 중지될 때까지 작업 실행
            job = CoroutineScope(Dispatchers.Main).launch {
                var currentDecibel = decibel
                    while (isRecording) {
                        delay(1000L) // 1초마다 데시벨 측정
                        val amplitude = it.maxAmplitude
                            val userDecibel = (20 * log10(amplitude.toDouble())).toInt()
                        if (currentDecibel.plus(20) < userDecibel ){
                            Log.d("userDecibel",userDecibel.toString())
                            Log.d("currentDecibel",currentDecibel.toString())
                            count1 += 1
                            binding.countText.text = count1.toString()+"개"
                        }
                    }

            }
        }


    }


}

