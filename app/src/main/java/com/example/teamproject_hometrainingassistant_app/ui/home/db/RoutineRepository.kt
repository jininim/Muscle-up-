package com.example.teamproject_hometrainingassistant_app.ui.home.db

import androidx.lifecycle.LiveData


class RoutineRepository(private val routineDao: RoutineDao) {

    val allRoutine: LiveData<List<Routine>> = routineDao.getProduct()


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }
    suspend fun updateProduct(routine: Routine){
        routineDao.updateProduct(routine)
    }

    suspend fun deleteProduct(routine: Routine){
        routineDao.deleteProduct(routine)
    }

}