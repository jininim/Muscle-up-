package com.example.teamproject_hometrainingassistant_app.ui.exercise.Service

import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import retrofit2.Call
import retrofit2.http.GET

interface ExerciseService {
    @GET("/v3/1a805720-d9a8-442e-a1d5-55b6d34ebd49")
    fun listExercises(): Call<ExerciseDTO>
}