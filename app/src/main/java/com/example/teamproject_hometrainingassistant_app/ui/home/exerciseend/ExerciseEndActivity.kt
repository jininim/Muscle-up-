package com.example.teamproject_hometrainingassistant_app.ui.home.exerciseend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import kotlin.math.ceil
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.DBKey
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseEndBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExerciseEndActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExerciseEndBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userDB: DatabaseReference
    private lateinit var userName: String
    private lateinit var userImage: String
    private var count: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MutatingSharedPrefs", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        userDB = FirebaseDatabase.getInstance().reference.child(DBKey.DB_USER)

        val sharedPreferencesUser = getSharedPreferences("kakao", MODE_PRIVATE)

        userName = sharedPreferencesUser.getString("USER_NAME", "") ?: ""
        userImage = sharedPreferencesUser.getString("USER_IMAGE", "") ?: ""

        val currentDate = getCurrentDate() // 현재 시간 가져오기
        var dayCount = sharedPreferences.getInt("DayCount", 0) // 몇일차인지 카운트
        val savedDate = sharedPreferences.getString("CurrentDate", null) // 최근 날짜 저장

        val itemList = intent.getStringArrayListExtra("exerciseNames") // 운동 이름 리스트
        val exerciseSet = intent.getStringArrayListExtra("exerciseSet") // 운동 별 세트 수
        val exerciseTime = intent.getStringArrayListExtra("exerciseTime") // 운동 별 횟수
        val currentTimer = intent.getStringExtra("currentTimer") // 운동한 총 시간
        val startTime = intent.getStringExtra("startTime") // 운동 시작 시간

        val timeM = currentTimer?.split(":")?.get(0)?.toDouble()
        val timeS =  currentTimer?.split(":")?.get(1)?.toDouble()
        val calorie : Double? = (timeM?.times(60)?.times(0.14))?.plus((timeS?.times(0.14)!!))
        val currentTime = Calendar.getInstance()
        val month = currentTime.get(Calendar.MONTH)
        val day = currentTime.get(Calendar.DAY_OF_MONTH)
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val period = if (hour >= 12) "오후" else "오전"
        val formattedTime = String.format("%s %d:%02d", period, hour % 12, minute) // 운동 종료 시간

        val sharedPref = getSharedPreferences("count", MODE_PRIVATE)
        count = sharedPref.getInt("activityCount1", 0)

        count++

        with(sharedPref.edit()) {
            putInt("activityCount", count)
            apply()
        }

        uploadCalendarRoutine(month, day, itemList!!)

        // 현재 날짜와 저장된 날짜 비교
        if (savedDate != currentDate) {
            // 저장된 날짜와 현재 날짜가 다를 경우, 일 수 증가
            dayCount++
            sharedPreferences.edit()
                .putString("CurrentDate", currentDate)
                .putInt("DayCount", dayCount)
                .apply()
        }

        Log.d("time", (month+1).toString())
        Log.d("time", day.toString())

        //칼로리 표기
        if (calorie != null) {
            binding.calorie.text = ceil(calorie).toString()
        }
        // 일 수를 화면에 표시
        binding.routineTotalDate.text = "${dayCount}일차"
        binding.routineTotalTime.text = "$currentTimer"
        binding.startTime.text = "$startTime"
        binding.endTime.text = formattedTime
        binding.dateTextView.text = currentDate

        val recyclerView: RecyclerView = binding.routineEndRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ExerciseEndAdapter(itemList,exerciseSet!!, exerciseTime!!)
        recyclerView.adapter = adapter


        //운동 종료 버튼 클릭 시
        binding.routineEndSaveButton.setOnClickListener {
            Toast.makeText(this, "저장 성공!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ITEM_LIST",itemList)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadCalendarRoutine(month: Int, day: Int, itemList:List<String>) {
        val user = userDB.child(userName)
        var i = 1
        Log.d("list", itemList.toString())
//        for (i in itemList){
//            user.child("${month+1}").child("$day").child("${count}").setValue(i)
//        }
        itemList.forEach { item->
            user.child("${month+1}").child("$day").child("${count}").child("$i").setValue(item)
            i++
        }
    }

}