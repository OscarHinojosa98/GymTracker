package com.example.gymtracker.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayEntryDao {
    @Insert
    suspend fun insertDayEntry(dayEntry: DayEntry)

    @Update
    suspend fun updateDayEntry(dayEntry: DayEntry)

    @Delete
    suspend fun deleteDayEntry(dayEntry: DayEntry)



    @Query("SELECT * FROM day_entry_data_table")
    fun getAllEntries():LiveData<List<DayEntry>>


    //@Query("SELECT EXISTS(SELECT * FROM day_entry_data_table WHERE workout_day = :date)")
    //suspend fun isRowIsExist(date : String) : Boolean


    //@Query("SELECT * FROM day_entry_data_table WHERE workout_day = :date")
    //fun  getDayWithWorkouts(date:String): List<DayEntry>

}