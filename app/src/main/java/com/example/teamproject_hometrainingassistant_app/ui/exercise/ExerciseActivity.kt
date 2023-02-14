package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.R.drawable.ic_photo
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityAdapter
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityData
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.HorizontalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.home.HomeFragment

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExerciseBinding
class ExerciseActivity : AppCompatActivity() {

    lateinit var exerciseOptionAdapter: ExerciseOptionAdapter
    lateinit var exerciseCategoryAdapter: ExerciseCategoryAdapter
    lateinit var exerciseSearchAdapter: ExerciseSearchAdapter

    val optionDatas = mutableListOf<ExerciseOptionData>()
    val categoryDatas = mutableListOf<ExerciseCategoryData>()
    val searchDatas = mutableListOf<ExerciseSearchData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        binding.searchView.isSubmitButtonEnabled = true
        setContentView(binding.root)

        OptionRecycler()
        CategoryRecycler()
        SearchRecycler()

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun OptionRecycler(){
        exerciseOptionAdapter = ExerciseOptionAdapter(this)
        binding.exerciseOptionRecyclerView.adapter = exerciseOptionAdapter
        binding.exerciseOptionRecyclerView.addItemDecoration(HorizontalItemDecorator(0))

        optionDatas.apply {
            add(ExerciseOptionData(text = "즐겨찾기"))
            add(ExerciseOptionData(text = "자주하는"))
            add(ExerciseOptionData(text = "최근"))
            add(ExerciseOptionData(text = "커스텀"))
            exerciseOptionAdapter.datas = optionDatas
            exerciseOptionAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun CategoryRecycler(){
        exerciseCategoryAdapter = ExerciseCategoryAdapter(this)
        binding.exerciseCategoryRecyclerView.adapter = exerciseCategoryAdapter
        binding.exerciseCategoryRecyclerView.addItemDecoration(HorizontalItemDecorator(0))

        categoryDatas.apply {
            add(ExerciseCategoryData(text = "가슴"))
            add(ExerciseCategoryData(text = "등"))
            add(ExerciseCategoryData(text = "어깨"))
            add(ExerciseCategoryData(text = "복근"))
            add(ExerciseCategoryData(text = "둔근"))
            add(ExerciseCategoryData(text = "하체"))
            add(ExerciseCategoryData(text = "이두"))
            add(ExerciseCategoryData(text = "삼두"))

            exerciseCategoryAdapter.datas = categoryDatas
            exerciseCategoryAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun SearchRecycler(){
        exerciseSearchAdapter = ExerciseSearchAdapter(this)
        binding.exerciseSearchRecyclerView.adapter = exerciseSearchAdapter
        binding.exerciseSearchRecyclerView.addItemDecoration(VerticalItemDecorator(0))

        searchDatas.apply {
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "줄넘기"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "점핑 잭"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "마운틴 클라이머"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "러시안 트위스트"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "시티드 니업"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "크런치"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "런지"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "플랭크"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "푸시업"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "스쿼트"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "턱걸이"))
            exerciseSearchAdapter.datas = searchDatas
            exerciseSearchAdapter.notifyDataSetChanged()
        }
    }
}