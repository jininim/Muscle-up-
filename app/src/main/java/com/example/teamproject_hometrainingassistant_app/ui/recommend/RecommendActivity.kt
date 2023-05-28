package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRecommendBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRecommendBinding
class RecommendActivity : AppCompatActivity() {

    lateinit var recommendAdapter: RecommendAdapter
    val recommendDatas = mutableListOf<RecommendData>()

    private val videoList : ArrayList<String> = ArrayList()
    private val videoTitleList : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoTitleList.add("3개월 맨몸 운동 홈트레이닝")
        videoTitleList.add("기발한 세트법으로 만들어진 최고의 맨몸운동 루틴")
        videoTitleList.add("맨몸 전신운동 홈트레이닝 7가지")
        videoTitleList.add("15분 체지방 100% 녹여버리는 루틴")

        initRecycler()

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(){
        recommendAdapter = RecommendAdapter(this, videoTitleList)
        binding.recommendRecyclerView.adapter = recommendAdapter
        binding.recommendRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recommendRecyclerView.addItemDecoration(VerticalItemDecorator(0))
    }
}