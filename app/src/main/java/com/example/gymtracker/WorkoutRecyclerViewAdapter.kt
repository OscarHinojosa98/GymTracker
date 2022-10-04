package com.example.gymtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.db.DayEntry

class WorkoutRecyclerViewAdapter(
    private val clickListener:(DayEntry)->Unit
):RecyclerView.Adapter<WorkoutViewHolder>() {

    private val workoutList = ArrayList<DayEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return WorkoutViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workoutList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    fun setList(dayEntries:List<DayEntry>,date:String){
        workoutList.clear()
        dayEntries.forEach { item ->
            if (item.entryDate == date)
                workoutList.add(item)
        }
    }

}

class WorkoutViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(dayEntry: DayEntry,clickListener:(DayEntry)->Unit){
        val nameTextView = view.findViewById<TextView>(R.id.tvName)
        val weightTextView = view.findViewById<TextView>(R.id.tvWeight)
        val repsTextView = view.findViewById<TextView>(R.id.tvReps)
        nameTextView.text = dayEntry.workout
        weightTextView.text = "${dayEntry.weight} lbs"
        repsTextView.text = "${dayEntry.reps} reps"
        view.setOnClickListener{
            clickListener(dayEntry)
        }
    }
}