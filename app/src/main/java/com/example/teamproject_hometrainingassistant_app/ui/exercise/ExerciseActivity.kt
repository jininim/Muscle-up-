package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.HorizontalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import com.example.teamproject_hometrainingassistant_app.ui.exercise.Service.ExerciseService
import com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter.ExerciseCategoryAdapter
import com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter.ExerciseOptionAdapter
import com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter.ExerciseSearchAdapter
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseCategoryData
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseOptionData
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseSearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExerciseBinding
class ExerciseActivity : AppCompatActivity() {

    lateinit var exerciseOptionAdapter: ExerciseOptionAdapter
    lateinit var exerciseCategoryAdapter: ExerciseCategoryAdapter
    private lateinit var exerciseSearchAdapter: ExerciseSearchAdapter

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

        exerciseSearchAdapter = ExerciseSearchAdapter(this)
        binding.exerciseSearchRecyclerView.apply {
            adapter = exerciseSearchAdapter
            layoutManager = LinearLayoutManager(context)
        }

        getExerciseList()

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

    private fun getExerciseList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExerciseService::class.java).also {
            it.listExercises()
                .enqueue(object : Callback<ExerciseDTO>{
                    override fun onResponse(
                        call: Call<ExerciseDTO>,
                        response: Response<ExerciseDTO>
                    ) {
                        if(response.isSuccessful.not()){
                            Log.d("ExerciseActivity", "response fail")
                            return
                        }

                        Log.d("성공", "${response.body()}")
                        response.body()?.let{ exerciseDTO ->
                            exerciseSearchAdapter.submitList(exerciseDTO.exercise)
                        }
                    }

                    override fun onFailure(call: Call<ExerciseDTO>, t: Throwable) {

                    }
                })
        }
    }
}