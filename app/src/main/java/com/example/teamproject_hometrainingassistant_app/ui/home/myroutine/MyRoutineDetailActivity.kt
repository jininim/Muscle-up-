package com.example.teamproject_hometrainingassistant_app.ui.home.myroutine

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_hometrainingassistant_app.DecibelActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMyRoutineDetailBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.exercisestart.ExerciseStartActivity

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMyRoutineDetailBinding
class MyRoutineDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRoutineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 액티비티에서 가져온 데이터
        val data = intent.getStringExtra("text")
        val trimmedString = data!!.replace("[", "").replace("]", "")
        val itemList: ArrayList<String> = ArrayList(trimmedString.split(", "))

        // RecyclerView 설정
        val recyclerView: RecyclerView = binding.routineDetailRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = MyRoutineAdapter(itemList)
        recyclerView.adapter = adapter


        binding.decibelStartButton.setOnClickListener {
            val intent = Intent(this, DecibelActivity::class.java)
            intent.run { startActivity(this) }
        }

        binding.myRoutineStartButton.setOnClickListener {
            val intent = Intent(this,ExerciseStartActivity::class.java)
            intent.putExtra("itemList", itemList)
            intent.run { startActivity(intent) }
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}