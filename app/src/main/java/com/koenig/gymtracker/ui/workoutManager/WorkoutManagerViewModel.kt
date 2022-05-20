package com.koenig.gymtracker.ui.workoutManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutManager
import com.koenig.gymtracker.models.workoutModel.WorkoutModel

class WorkoutManagerViewModel : ViewModel() {

    // Is the live data list of workout models
    private val workouts = MutableLiveData<List<WorkoutModel>>()

    // Whenever the live data list has changed the subscriber are notified
    val observableWorkouts: LiveData<List<WorkoutModel>>
        get() = workouts

    // Called when started
    init {
        load()
    }

    // Fills the list from model
    fun load() {
        workouts.value = WorkoutManager.findAll()
    }

    // Adds new Workout
    fun addWorkout(workoutModel: WorkoutModel) {
        WorkoutManager.createWorkout(workoutModel)
    }

    // Updates the Workout
    fun updateWorkout(workoutModel: WorkoutModel, position: Int) {
        WorkoutManager.updateWorkout(workoutModel, position)
    }

    // Adds new exercise to workout
    fun addExercise(exerciseModel: ExerciseModel, workoutId: Int) {
        WorkoutManager.addExerciseToWorkout(exerciseModel, workoutId)
    }

    // Updates the exercise
    fun updateExercise(exerciseModel: ExerciseModel, workoutId: Int, position: Int) {
        WorkoutManager.updateExerciseForWorkout(exerciseModel, workoutId, position)
    }

    // Starts the workout
    fun getCurrentWorkout(workoutId: Int): WorkoutModel {
        return WorkoutManager.getCurrentWorkout(workoutId)
    }
}