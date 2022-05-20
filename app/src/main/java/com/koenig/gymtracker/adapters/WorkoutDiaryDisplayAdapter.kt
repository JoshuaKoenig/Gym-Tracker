package com.koenig.gymtracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koenig.gymtracker.databinding.ListItemExerciseDiaryBinding
import com.koenig.gymtracker.models.exerciseDiaryModel.ExerciseDiaryModel

interface WorkoutDiaryDisplayClickListener {
    fun onExerciseClick(exercise: ExerciseDiaryModel, position: Int)
}

class WorkoutDiaryDisplayAdapter constructor(
    private var exercises: List<ExerciseDiaryModel>,
    private val listener: WorkoutDiaryDisplayClickListener
) : RecyclerView.Adapter<WorkoutDiaryDisplayAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutDiaryDisplayAdapter.MainHolder {
        val binding =
            ListItemExerciseDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutDiaryDisplayAdapter.MainHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exercises.size

    inner class MainHolder(val binding: ListItemExerciseDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseDiaryModel, listener: WorkoutDiaryDisplayClickListener) {
            // Bind the workout
            binding.exerciseDiary = exercise

            // Set the click listener to list item
            binding.root.setOnClickListener { listener.onExerciseClick(exercise, adapterPosition) }

            // Execute the binding
            binding.executePendingBindings()
        }
    }
}