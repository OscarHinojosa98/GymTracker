package com.example.gymtracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_entry_data_table")
data class DayEntry (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="workout_id")
    var id:Int,
    @ColumnInfo(name = "workout_name")
    var workout:String,
    @ColumnInfo(name = "workout_weight")
    var weight:Int,
    @ColumnInfo(name = "workout_reps")
    var reps:Int,
    @ColumnInfo(name = "workout_day")
    var entryDate:String

)
