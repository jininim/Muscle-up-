package com.example.teamproject_hometrainingassistant_app.ui.exercise.Service

import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import retrofit2.Call
import retrofit2.http.GET

interface ExerciseService {
    @GET("/v3/3376c207-404e-48ca-a92c-cbe54e6958e9")
    fun listExercises(): Call<ExerciseDTO>
}