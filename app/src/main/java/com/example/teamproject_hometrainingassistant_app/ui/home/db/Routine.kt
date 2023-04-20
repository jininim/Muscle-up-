package com.example.teamproject_hometrainingassistant_app.ui.home.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//선택한 운동 종목

@Entity(tableName = "routine_table")
data class Routine(
    @PrimaryKey(autoGenerate = true) //primary key는 자동으로 만들게 합니다.
    val id:Int,
    @ColumnInfo(name = "routine") var routine : String,
    @ColumnInfo(name ="check") var check: Boolean
)