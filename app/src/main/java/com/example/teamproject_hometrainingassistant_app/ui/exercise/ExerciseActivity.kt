package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityExerciseBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import com.example.teamproject_hometrainingassistant_app.ui.exercise.Service.ExerciseService
import com.example.teamproject_hometrainingassistant_app.ui.exercise.adapter.ExerciseSearchAdapter
import com.example.teamproject_hometrainingassistant_app.ui.exercise.model.ExerciseModel

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
    private lateinit var currentFilteredExerciseList: List<ExerciseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = HomeFragment()

        binding.optionButton1.setOnClickListener {
            applyTypeFilter("팔")
        }
        binding.optionButton2.setOnClickListener {
            applyTypeFilter("가슴")
        }
        binding.optionButton3.setOnClickListener {
            applyTypeFilter("어깨")
        }
        binding.optionButton4.setOnClickListener {
            applyTypeFilter("등")
        }
        binding.optionButton5.setOnClickListener {
            applyTypeFilter("복부")
        }
        binding.optionButton6.setOnClickListener {
            applyTypeFilter("하체")
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                applySearchFilter(p0.toString())
            }

        })

        //리사이클러뷰 어답터 운동리스트
        exerciseSearchAdapter = ExerciseSearchAdapter(onItemClicked = {exerciseModel ->
            val intent = Intent(this, ExerciseInformation::class.java)
            intent.putExtra("name", exerciseModel.name)
            intent.putExtra("exerciseUrl", exerciseModel.exersiceUrl)
            intent.putExtra("guide", exerciseModel.guide)
            intent.putExtra("youtubeUrl", exerciseModel.youtubeUrl)
            intent.putExtra("type", exerciseModel.type)
            intent.run { startActivity(intent) }
        }, onCheckBoxClick = { name, time, url ->
            var nameList: ArrayList<String> = name
            var timeList: ArrayList<String> = time
            var urlList: ArrayList<String> = url

            binding.fabadd.setOnClickListener {
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("NAME_LIST",nameList)
                intent.putExtra("TIME_LIST", timeList)
                intent.putExtra("URL_LIST", urlList)
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

    private fun applyTypeFilter(filterType: String) {
        val filteredList = currentFilteredExerciseList.filter { exercise ->
            exercise.type.contains(filterType)
        }
        exerciseSearchAdapter.submitList(filteredList)
    }

    private fun applySearchFilter(query: String) {
        val filteredList = currentFilteredExerciseList.filter { exercise ->
            exercise.name.contains(query, ignoreCase = true)
        }
        exerciseSearchAdapter.submitList(filteredList)
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
                            currentFilteredExerciseList = exerciseDTO.exercise
                            exerciseSearchAdapter.submitList(exerciseDTO.exercise)
                        }
                    }

                    override fun onFailure(call: Call<ExerciseDTO>, t: Throwable) {

                    }
                })
        }
    }

//    private fun filterExerciseList(menu: String) {
//        val filteredList = if (menu == "전체") {
//            exerciseViewModel.getAllExerciseList()
//        } else {
//            exerciseViewModel.getFilteredExerciseList(menu)
//        }
//        exerciseSearchAdapter.submitList(filteredList)
//        Log.d("filter", exerciseViewModel.getFilteredExerciseList(menu).toString())
//    }

//    private fun filterExerciseListBySearch(query: String?){
//        val filteredList = if (query.isNullOrEmpty()){
//            allExerciseList
//        }else{
//            allExerciseList.filter { it.name.contains(query, true) }
//        }
//        exerciseSearchAdapter.submitList(filteredList)
//    }
}