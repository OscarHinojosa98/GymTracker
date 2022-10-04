package com.example.gymtracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DayEntry::class], version = 2, exportSchema = false)
abstract class DayEntryDatabase: RoomDatabase() {
    abstract fun dayEntryDao():DayEntryDao
    companion object{
        @Volatile
        private var INSTANCE: DayEntryDatabase? = null
        fun getInstance(context: Context):DayEntryDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DayEntryDatabase::class.java,
                        "day_entry_database"
                    )
                        //.fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}