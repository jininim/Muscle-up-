package com.example.teamproject_hometrainingassistant_app.ui.exercise.Service

import com.example.teamproject_hometrainingassistant_app.ui.exercise.DTO.ExerciseDTO
import retrofit2.Call
import retrofit2.http.GET

interface ExerciseService {
    @GET("/v3/d5dd8382-c50b-43e2-a27e-798ab20ddf1f")
    fun listExercises(): Call<ExerciseDTO>
}