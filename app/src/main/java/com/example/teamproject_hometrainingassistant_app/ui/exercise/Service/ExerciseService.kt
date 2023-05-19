package com.example.teamproject_hometrainingassistant_app.ui.exercise.Service

import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import retrofit2.Call
import retrofit2.http.GET

interface ExerciseService {
    @GET("/v3/9f5702c7-5034-42e8-a9e8-546ff5c78289")
    fun listExercises(): Call<ExerciseDTO>
}