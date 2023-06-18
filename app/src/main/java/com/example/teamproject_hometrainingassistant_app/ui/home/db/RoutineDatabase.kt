package com.example.teamproject_hometrainingassistant_app.ui.home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Routine::class], version = 3, exportSchema = true)
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
                ).addMigrations(Migration1To2())
                 .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    class Migration1To2 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 이전 버전(1)에서 새 버전(2)으로의 스키마 변경 작업을 수행합니다.
            database.execSQL("CREATE TABLE IF NOT EXISTS new_table (id INTEGER PRIMARY KEY NOT NULL, routine TEXT NOT NULL, time TEXT NOT NULL, url TEXT NOT NULL, isChecked INTEGER NOT NULL)")
            database.execSQL("INSERT INTO new_table (id, routine, time, url, isChecked) SELECT id, routine, '', '', 0 FROM routine_table")
            database.execSQL("DROP TABLE routine_table")
            database.execSQL("ALTER TABLE new_table RENAME TO routine_table")
        }
    }

}
