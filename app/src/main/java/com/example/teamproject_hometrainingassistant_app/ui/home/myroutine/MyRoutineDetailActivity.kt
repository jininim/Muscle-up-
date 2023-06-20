package com.example.teamproject_hometrainingassistant_app.ui.home.myroutine

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMyRoutineDetailBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ItemMyroutineDetailBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartActivity
import kotlinx.coroutines.*
import java.io.IOException

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMyRoutineDetailBinding
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class MyRoutineDetailActivity : AppCompatActivity(), MyRoutineDialog.OnDialogResultListener {

    private var fileName: String = ""

    private var recorder: MediaRecorder? = null
    private var isRecording = false
    private var job: Job? = null

    private var editPosition: Int = 0

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var editCountTime: ArrayList<String> = arrayListOf()

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
            Toast.makeText(this@MyRoutineDetailActivity, "사용자의 주변 소음을 측정합니다", Toast.LENGTH_LONG)
                .show()
            start() // 녹음 시작
        }
    }

    private fun stopRecording() {
        isRecording = false
        Toast.makeText(this, "측정 중지", Toast.LENGTH_SHORT).show()
        job?.cancel()
        recorder?.apply {
            stop() //녹음 중지
            release()
        }
        recorder = null
        binding.myRoutineStartButton.isClickable = true
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRoutineDetailBinding.inflate(layoutInflater)

        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(
            this, permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )
        setContentView(binding.root)

        // 이전 액티비티에서 가져온 데이터
        val routineName = intent.getStringExtra("ROUTINE_NAME")
        val routineTime = intent.getStringExtra("ROUTINE_TIME")
        val routineUrl = intent.getStringExtra("ROUTINE_URL")

        val trimmedName = routineName!!.replace("[", "").replace("]", "")
        val itemList: ArrayList<String> = ArrayList(trimmedName.split(", "))

        val trimmedTime = routineTime!!.replace("[","").replace("]","")
        val timeList: ArrayList<String> = ArrayList(trimmedTime.split(", "))
        editCountTime = timeList

        val trimmedUrl = routineUrl!!.replace("[","").replace("]","")
        val urlList: ArrayList<String> = ArrayList(trimmedUrl.split(", "))

        // RecyclerView 설정
        val recyclerView: RecyclerView = binding.routineDetailRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = MyRoutineAdapter(
            itemList,
            timeList,
            onItemClick = { timeText, position ->
                editPosition = position
                val countSet = timeText.split(" ")
                val count = countSet[0].replace("회", "")
                val set = countSet[1].replace("세트", "")
                showTimeDialog(count, set)
            }
        )
        recyclerView.adapter = adapter

        var check = false
        //사용자 소음 측정 버튼 클릭 시
        binding.decibelStartButton.setOnClickListener {
            check = !check
            if (check) {
                binding.myRoutineStartButton.isClickable = false
                binding.decibelStartButton.text = "사용자 소음 측정 중지"
                startRecording() // 녹음 시작
                getDb() //데시벨 측정
            } else {
                stopRecording()
                binding.myRoutineStartButton.isClickable = true
                binding.decibelStartButton.text = "사용자 소음 측정"
            }
        }

        binding.myRoutineStartButton.setOnClickListener {
            val intent = Intent(this, ExerciseStartActivity::class.java)
            intent.putExtra("itemList", itemList)
            intent.putExtra("timeList", timeList)
            intent.putExtra("urlList", urlList)
            intent.putExtra("decibel", binding.dbText.text)
            intent.run { startActivity(intent) }
        }
        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    private fun showTimeDialog(count: String, set: String){
        val fragmentManager = supportFragmentManager
        val dialogFragment = MyRoutineDialog.newInstance(count, set)
        dialogFragment.setOnDialogResultListener(this)
        dialogFragment.show(fragmentManager, "count_set_dialog")
    }

    override fun onTimeEdited(count: String, set: String) { // 운동 횟수 수정 후 로직
        val editText = "${count}회 ${set}세트"
        val adapter = binding.routineDetailRecyclerView.adapter as MyRoutineAdapter
        editCountTime[editPosition] = editText
        adapter.setCountUpdate(editText, editPosition)
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
    }

    //데시벨 측정 함수
    @SuppressLint("SetTextI18n")
    private fun getDb() {
        recorder?.let {
            isRecording = true
            // 녹음이 중지될 때까지 작업 실행, 5초 후 종료
            job = CoroutineScope(Dispatchers.Main).launch {
                withTimeout(5000L) { // 5초 동안 실행
                    while (isRecording) {
                        delay(200L) // 0.2초마다 데시벨 측정
                        val amplitude = it.maxAmplitude
                        binding.dbText.text =
                            (20 * kotlin.math.log10(amplitude.toDouble())).toInt().toString()
                    }
                }
            }
        }
    }


}