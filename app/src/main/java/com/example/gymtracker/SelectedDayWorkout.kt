package com.example.gymtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.FragmentSelectedDayWorkoutBinding
import com.example.gymtracker.db.DayEntry
import com.example.gymtracker.db.DayEntryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectedDayWorkout : Fragment() {
    private lateinit var binding: FragmentSelectedDayWorkoutBinding
    private lateinit var viewModel: DayEntryViewModel


    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var adapter: WorkoutRecyclerViewAdapter
    private var workoutId= -1

    private lateinit var selectedWorkout:DayEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dao = DayEntryDatabase.getInstance(this.context!!).dayEntryDao()
        val factory = DayEntryViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(DayEntryViewModel::class.java)
        binding = FragmentSelectedDayWorkoutBinding.inflate(inflater,container,false)

        val date: String = requireArguments().getString("selected_date").toString()

        binding.tvDateSelected.text = date


        initRecyclerView(date)

        binding.btnBack.setOnClickListener(){
            it.findNavController().navigate(R.id.action_selectedDayWorkout_to_navigation_dashboard)
        }



        binding.btnAddWorkout.setOnClickListener(){
            val bundle = bundleOf("selected_date" to date,"workout_id" to workoutId)
            it.findNavController().navigate(R.id.action_selectedDayWorkout_to_chooseWorkoutFragment,bundle)
        }

        binding.btnEdit.setOnClickListener(){
            val bundle = bundleOf("selected_date" to date,"workout_id" to workoutId,"selected_workout" to selectedWorkout.workout)
            it.findNavController().navigate(R.id.action_selectedDayWorkout_to_chooseWorkoutFragment,bundle)
        }

        binding.btnDelete.setOnClickListener(){
            viewModel.deleteDayEntry(
                DayEntry(
                    selectedWorkout.id,
                    selectedWorkout.workout,
                    selectedWorkout.weight,
                    selectedWorkout.reps,
                    date
                )
            )
            workoutId=-1
        }

        return binding.root
    }
    private fun initRecyclerView(date:String){
        binding.rvWorkouts.layoutManager = LinearLayoutManager(context)
        adapter = WorkoutRecyclerViewAdapter{
            selectedItem:DayEntry -> listItemClicked(selectedItem)
            
        }
        binding.rvWorkouts.adapter = adapter

        displayWorkoutList(date)

    }
    private fun displayWorkoutList(date:String){
        viewModel.dayEntries.observe(viewLifecycleOwner,{
            adapter.setList(it,date)
            adapter.notifyDataSetChanged()
        })

    }

    private fun listItemClicked(dayEntry: DayEntry){
        selectedWorkout = dayEntry
        binding.btnEdit.isVisible = true
        binding.btnDelete.isVisible = true
        workoutId = selectedWorkout.id


        Toast.makeText(
            requireContext(),
            "You have Selected ${selectedWorkout.workout}",
            Toast.LENGTH_SHORT
        ).show()
    }

}