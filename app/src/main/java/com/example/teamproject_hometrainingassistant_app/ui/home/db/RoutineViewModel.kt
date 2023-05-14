package com.example.teamproject_hometrainingassistant_app.ui.home.db

import android.app.Application
import androidx.lifecycle.*


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    //db 데이터
    val readAllData : LiveData<List<Routine>>

    private val repository : RoutineRepository

    init {
        val productDao = RoutineDatabase.getDatabase(application).productDao()
        repository = RoutineRepository(productDao)
        readAllData = repository.allRoutine
    }

    fun addProduct(routine: Routine){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(routine)
        }
    }

    fun deleteProduct(routine: Routine){
        viewModelScope.launch(Dispatchers.IO) {
                repository.deleteProduct(routine)
        }
    }

    fun updateProduct(routine: Routine){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateProduct(routine)
        }
    }





}