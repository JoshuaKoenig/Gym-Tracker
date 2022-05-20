package com.koenig.gymtracker.models.workoutModel

import com.koenig.gymtracker.models.exerciseModel.ExerciseModel

interface WorkoutStore {

    fun findAll(): List<WorkoutModel>
    fun findById(id: Int): WorkoutModel?
    fun createWorkout(workout: WorkoutModel)
    fun updateWorkout(workout: WorkoutModel, index: Int)
    fun addExerciseToWorkout(exercise: ExerciseModel, workoutId: Int)
    fun updateExerciseForWorkout(exercise: ExerciseModel, workoutId: Int, position: Int)
    fun startWorkout(workout: WorkoutModel)
    fun stopWorkout(workout: WorkoutModel)
}