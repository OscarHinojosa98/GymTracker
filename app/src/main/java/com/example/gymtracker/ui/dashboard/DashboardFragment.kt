package com.example.gymtracker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gymtracker.DayEntryViewModel
import com.example.gymtracker.DayEntryViewModelFactory
import com.example.gymtracker.MainActivity
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentDashboardBinding
import com.example.gymtracker.db.DayEntry
import com.example.gymtracker.db.DayEntryDatabase


class DashboardFragment() : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: DayEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dao = DayEntryDatabase.getInstance(this.context!!).dayEntryDao()
        val factory = DayEntryViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(DayEntryViewModel::class.java)

        var msg = ""
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.calendarView.setOnDateChangeListener{ View, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            msg = "${month + 1}/$dayOfMonth/$year"

            if  (msg !=""){
                binding.btnRoutine.isVisible = true
                //if(dao.isRowIsExist(msg)){
                //    Toast.makeText(activity, "This is Empty", Toast.LENGTH_LONG).show()
                //}
                //else{
                //    Toast.makeText(activity, "This is not Empty", Toast.LENGTH_LONG).show()
                //}
        }
            //Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }

        binding.btnRoutine.setOnClickListener(){
            val bundle = bundleOf("selected_date" to msg)
            /*viewModel.insertDayEntry(
                DayEntry(
                    0,
                "Chest Press",
                    100,
                    10,
                    msg
                )
            )*/




          it.findNavController().navigate(R.id.action_navigation_dashboard_to_selectedDayWorkout,bundle)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}