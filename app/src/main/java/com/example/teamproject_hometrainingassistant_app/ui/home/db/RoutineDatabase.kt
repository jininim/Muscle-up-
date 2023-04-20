package com.example.teamproject_hometrainingassistant_app.ui.home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Routine::class], version = 1, exportSchema = false)
abstract class RoutineDatabase : RoomDatabase() {

    abstract fun productDao() : RoutineDao
    companion object {
        @Volatile //다른 thread에서 접근 가능하게 만드는 것입니다.
        private var INSTANCE: RoutineDatabase? = null
        fun getDatabase(context: Context): RoutineDatabase {

            return INSTANCE ?: synchronized(this) {//synchronized는 새로운 데이터베이스를 instance시킵니다.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoutineDatabase::class.java,
                    "routine_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
