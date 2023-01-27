package com.example.teamproject_hometrainingassistant_app

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PackageManagerCompat
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityDecibelBinding
import kotlinx.coroutines.*
import java.io.IOException

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class DecibelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDecibelBinding

    private var fileName: String = ""

    private  var recorder: MediaRecorder? = null
    private var isRecording = false
    private var job: Job? = null


    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

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
            Toast.makeText(this@DecibelActivity,"녹음 시작", Toast.LENGTH_LONG).show()
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
        Toast.makeText(this,"녹음 중지", Toast.LENGTH_LONG).show()
        recorder = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDecibelBinding.inflate(layoutInflater)
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        setContentView(binding.root)
        //녹음시작 버튼 클릭
        binding.startButton.setOnClickListener {
            binding.stopButton.isEnabled = true
            binding.startButton.isEnabled = false
            startRecording() // 녹음 시작
            getDb() //데시벨 측정
        }
        //녹음 중지 버튼 클릭
        binding.stopButton.isEnabled = false
        binding.stopButton.setOnClickListener {
            stopRecording()// 녹음중지
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
        }
    }
    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
    }
    //데시벨 측정 함수
    private fun getDb(){
        recorder?.let {
            isRecording = true
            //녹음이 중지 될 때 까지 작업 실행 텍스트에 데시벨 값 할당.
            job = CoroutineScope(Dispatchers.Main).launch {
                while (isRecording) {
                    delay(200L) // 0.2초마다 데시벨 측정
                    val amplitude = it.maxAmplitude
                    binding.textViewDecibel.text = (20 * kotlin.math.log10(amplitude.toDouble())).toInt().toString()
                    binding.progressBar.progress = (20 * kotlin.math.log10(amplitude.toDouble())).toInt()
                }
            }

        }


    }


}