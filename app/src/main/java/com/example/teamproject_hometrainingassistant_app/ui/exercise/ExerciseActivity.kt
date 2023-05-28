package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.HorizontalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import com.example.teamproject_hometrainingassistant_app.ui.exercise.Service.ExerciseService
import com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter.ExerciseSearchAdapter

import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseSearchData
import com.example.teamproject_hometrainingassistant_app.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExerciseBinding
class ExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseSearchAdapter: ExerciseSearchAdapter

    val searchDatas = mutableListOf<ExerciseSearchData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리사이클러뷰 어답터 운동리스트
        exerciseSearchAdapter = ExerciseSearchAdapter(onItemClicked = {exerciseModel ->
            val intent = Intent(this, ExerciseInformation::class.java)
            intent.putExtra("name", exerciseModel.name)
            intent.putExtra("exerciseUrl", exerciseModel.exerciseUrl)
            intent.putExtra("guide", exerciseModel.guide)
            intent.putExtra("youtubeUrl", exerciseModel.youtubeUrl)
            intent.putExtra("type", exerciseModel.type)
            intent.run { startActivity(intent) }
        }, oncheckBoxClick = { name, time ->
            var nameList: ArrayList<String> = name
            val timeList: ArrayList<String> = time

            val bundle = Bundle()
            bundle.putStringArrayList("time", timeList)

            val homeFragment = HomeFragment()
            homeFragment.arguments = bundle

            Log.d("time", timeList.toString())
            binding.fabadd.setOnClickListener {
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("NAME_LIST",nameList)
                startActivity(intent)
                finish()

            }

        })
        binding.exerciseSearchRecyclerView.apply {
            adapter = exerciseSearchAdapter
            layoutManager = LinearLayoutManager(context)
        }
        getExerciseList()

        //뒤로가기 버튼 클릭 시
        binding.exerciseBackButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
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