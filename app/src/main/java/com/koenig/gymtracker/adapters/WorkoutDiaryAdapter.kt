package com.koenig.gymtracker.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koenig.gymtracker.databinding.ListItemDiaryWorkoutBinding
import com.koenig.gymtracker.models.diaryModel.DiaryWorkoutModel


interface WorkoutDiaryClickListener {
    fun onWorkoutClick(workout: DiaryWorkoutModel)
}

class WorkoutDiaryAdapter constructor(
    private var diaryWorkouts: List<DiaryWorkoutModel>,
    private val listener: WorkoutDiaryClickListener
) : RecyclerView.Adapter<WorkoutDiaryAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutDiaryAdapter.MainHolder {
        val binding =
            ListItemDiaryWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutDiaryAdapter.MainHolder, position: Int) {
        val workout = diaryWorkouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = diaryWorkouts.size

    inner class MainHolder(val binding: ListItemDiaryWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: DiaryWorkoutModel, listener: WorkoutDiaryClickListener) {
            // Bind the workout
            binding.workout = workout

            // Set the click listener to list item
            binding.root.setOnClickListener { listener.onWorkoutClick(workout) }

            // Set the color of the image
            binding.imageColor.setBackgroundColor(Color.parseColor(workout.workoutColor))

            // Edit the workout
            // binding.btEditWorkout.setOnClickListener { listener.onWorkoutEditClick(workout, adapterPosition) }

            // Execute the binding
            binding.executePendingBindings()
        }
    }
}