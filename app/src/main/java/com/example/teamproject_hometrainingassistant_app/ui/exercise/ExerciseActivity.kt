package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.R.drawable.ic_photo
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityAdapter
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityData
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.HorizontalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator

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
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "내가 쓴 글"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "댓글 단 글"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "즐겨찾기"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "핫 게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "베스트 게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "자유게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "질문게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "운동정보게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "오운완게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "꿀팁게시판"))
            add(ExerciseSearchData(img = R.drawable.ic_photo, text = "루틴추천게시판"))
            exerciseSearchAdapter.datas = searchDatas
            exerciseSearchAdapter.notifyDataSetChanged()
        }
    }
}