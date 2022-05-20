package com.koenig.gymtracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koenig.gymtracker.databinding.ListItemExerciseBinding
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel

interface ExerciseClickListener {
    fun onExerciseClick(exercise: ExerciseModel, exercisePosition: Int)
}

class WorkoutDisplayAdapter constructor(
    private var exercises: List<ExerciseModel>,
    private val listener: ExerciseClickListener
) : RecyclerView.Adapter<WorkoutDisplayAdapter.MainHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutDisplayAdapter.MainHolder {
        val binding =
            ListItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutDisplayAdapter.MainHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exercises.size

    inner class MainHolder(val binding: ListItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseModel, listener: ExerciseClickListener) {
            // Bind the workout
            binding.exercise = exercise

            // Set the click listener to list item
            binding.root.setOnClickListener { listener.onExerciseClick(exercise, adapterPosition) }

            // Execute the binding
            binding.executePendingBindings()
        }
    }
}