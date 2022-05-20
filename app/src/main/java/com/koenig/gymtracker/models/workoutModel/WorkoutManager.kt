package com.koenig.gymtracker.models.workoutModel

import com.koenig.gymtracker.models.exerciseModel.ExerciseModel

var lastId = 0

internal fun getId(): Int {
    return lastId++
}

object WorkoutManager : WorkoutStore {
    private val workouts = ArrayList<WorkoutModel>()

    override fun findAll(): List<WorkoutModel> {
        return workouts
    }

    override fun findById(id: Int): WorkoutModel? {
        return workouts.find { it.workoutId == id }
    }

    override fun createWorkout(workout: WorkoutModel) {
        workout.workoutId = getId()
        workouts.add(workout)
    }

    override fun updateWorkout(workout: WorkoutModel, index: Int) {
        workouts[index] = workout
    }

    override fun addExerciseToWorkout(exercise: ExerciseModel, workoutId: Int) {
        workouts.find { it.workoutId == workoutId }?.exercises?.add(exercise)
    }

    override fun updateExerciseForWorkout(exercise: ExerciseModel, workoutId: Int, position: Int) {
        workouts.find { it.workoutId == workoutId }?.exercises?.set(position, exercise)
    }

    override fun startWorkout(workout: WorkoutModel) {
        workout.isStarted = true
    }

    override fun stopWorkout(workout: WorkoutModel) {
        workout.isStarted = false
    }

    fun getCurrentWorkout(workoutId: Int): WorkoutModel {
        return workouts.find { workoutModel -> workoutModel.workoutId == workoutId }!!
    }

}