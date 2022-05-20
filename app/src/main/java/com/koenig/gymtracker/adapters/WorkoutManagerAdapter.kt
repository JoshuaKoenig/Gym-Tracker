package com.koenig.gymtracker.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koenig.gymtracker.databinding.ListItemWorkoutBinding
import com.koenig.gymtracker.models.workoutModel.WorkoutModel

interface WorkoutClickListener {
    fun onWorkoutClick(workout: WorkoutModel)
    fun onWorkoutEditClick(workout: WorkoutModel, position: Int)
}

class WorkoutManagerAdapter constructor(
    private var workouts: List<WorkoutModel>,
    private val listener: WorkoutClickListener
) : RecyclerView.Adapter<WorkoutManagerAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutManagerAdapter.MainHolder {
        val binding =
            ListItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutManagerAdapter.MainHolder, position: Int) {
        val workout = workouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workouts.size

    inner class MainHolder(val binding: ListItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: WorkoutModel, listener: WorkoutClickListener) {
            // Bind the workout
            binding.workout = workout

            // Set the click listener to list item
            binding.root.setOnClickListener { listener.onWorkoutClick(workout) }

            // Set the color of the image
            binding.imageColor.setBackgroundColor(Color.parseColor(workout.workoutColor))

            // Edit the workout
            binding.btEditWorkout.setOnClickListener {
                listener.onWorkoutEditClick(
                    workout,
                    adapterPosition
                )
            }

            // Execute the binding
            binding.executePendingBindings()
        }
    }
}