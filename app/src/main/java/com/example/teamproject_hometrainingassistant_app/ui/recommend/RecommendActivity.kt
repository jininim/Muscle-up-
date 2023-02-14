package com.example.teamproject_hometrainingassistant_app.ui.recommend

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityRecommendBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.HorizontalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseCategoryData
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseOptionAdapter
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseOptionData
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseSearchData

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRecommendBinding
class RecommendActivity : AppCompatActivity() {

    lateinit var recommendAdapter: RecommendAdapter
    val recommendDatas = mutableListOf<RecommendData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(){
        recommendAdapter = RecommendAdapter(this)
        binding.recommendRecyclerView.adapter = recommendAdapter
        binding.recommendRecyclerView.addItemDecoration(VerticalItemDecorator(0))

        recommendDatas.apply {
            add(RecommendData(img = R.drawable.user, text = "최지원의 복근 조지기 루틴"))
            add(RecommendData(img = R.drawable.user, text = "유튜버 A의 3분할 맨몸 루틴"))
            add(RecommendData(img = R.drawable.user, text = "유튜버 B의 유산소 루틴"))
            add(RecommendData(img = R.drawable.user, text = "유튜버 C의 스트레칭"))

            recommendAdapter.datas = recommendDatas
            recommendAdapter.notifyDataSetChanged()
        }
    }
}