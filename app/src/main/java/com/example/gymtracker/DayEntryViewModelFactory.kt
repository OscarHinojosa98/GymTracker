package com.example.gymtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gymtracker.db.DayEntryDao
import java.lang.IllegalArgumentException

class DayEntryViewModelFactory(
    private val dao:DayEntryDao
):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DayEntryViewModel::class.java)){
            return DayEntryViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}