package com.example.teamproject_hometrainingassistant_app.ui.home.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.teamproject_hometrainingassistant_app.ui.home.db.Routine

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine_table ORDER BY id ASC")
    fun getProduct(): LiveData<List<Routine>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routine: Routine)

    @Update
    fun updateProduct(routine: Routine)

    //선택한 항목 삭제
    @Delete
    fun deleteProduct(routine: Routine)


}