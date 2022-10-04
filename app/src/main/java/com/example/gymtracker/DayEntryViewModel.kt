package com.example.gymtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.db.DayEntry
import com.example.gymtracker.db.DayEntryDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DayEntryViewModel(private val dao: DayEntryDao): ViewModel() {

    val dayEntries = dao.getAllEntries()


    fun insertDayEntry(dayEntry: DayEntry) = viewModelScope.launch {
        dao.insertDayEntry(dayEntry)
    }

    fun updateDayEntry(dayEntry: DayEntry) = viewModelScope.launch {
        dao.updateDayEntry(dayEntry)
    }

    fun deleteDayEntry(dayEntry: DayEntry) = viewModelScope.launch {
        dao.deleteDayEntry(dayEntry)
    }



   //fun isRowIsExist(id : String): Boolean = viewModelScope.launch {
   //     dao.isRowIsExist(id)
//}

}