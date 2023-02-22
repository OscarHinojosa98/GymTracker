package com.example.gymtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gymtracker.databinding.FragmentChooseWorkoutBinding
import com.example.gymtracker.db.DayEntry
import com.example.gymtracker.db.DayEntryDatabase


class ChooseWorkoutFragment : Fragment() {

    private lateinit var viewModel: DayEntryViewModel
    private lateinit var binding: FragmentChooseWorkoutBinding
    private lateinit var selectedWorkout:DayEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dao = DayEntryDatabase.getInstance(this.context!!).dayEntryDao()
        val factory = DayEntryViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(DayEntryViewModel::class.java)

        val date: String = requireArguments().getString("selected_date").toString()
        val selectedWorkoutId:Int = requireArguments().getInt("workout_id")
        var selectedWorkoutName:String = requireArguments().getString("selected_workout").toString()

        val workouts = resources.getStringArray(R.array.workout_list)
        val arrayAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,workouts)
        binding = FragmentChooseWorkoutBinding.inflate(inflater,container,false)
        binding.tvWorkoutName.setAdapter(arrayAdapter)
        binding.tvWorkoutName.hint = "Workout"

        if (selectedWorkoutId!=-1){
            binding.apply {
                tvWorkoutName.hint = selectedWorkoutName
            }
        }


        binding.btnSave.setOnClickListener(){
            val bundle = bundleOf("selected_date" to date)
            val name = binding.tvWorkoutName.text.toString()
            val weight = binding.etWeight.text.toString()
            val reps = binding.etReps.text.toString()
            if (binding.tvWorkoutName.text.isBlank() || weight=="" || reps==""){
                Toast.makeText(
                    requireContext(),
                    "You are missing information",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (selectedWorkoutId==-1) {
                viewModel.insertDayEntry(
                    DayEntry(
                        0,
                        name,
                        weight.toInt(),
                        reps.toInt(),
                        date
                    )
                )
                it.findNavController().navigate(R.id.action_chooseWorkoutFragment_to_selectedDayWorkout,bundle)
            }
            else{
                viewModel.updateDayEntry(
                    DayEntry(
                        selectedWorkoutId,
                        name,
                        weight.toInt(),
                        reps.toInt(),
                        date
                    )
                )
                it.findNavController().navigate(R.id.action_chooseWorkoutFragment_to_selectedDayWorkout,bundle)
            }
            //binding.tvWorkoutName.hint = "Workout"


        }
        return binding.root
    }

}