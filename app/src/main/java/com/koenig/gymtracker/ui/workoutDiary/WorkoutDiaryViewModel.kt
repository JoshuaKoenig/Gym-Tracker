package com.koenig.gymtracker.ui.workoutDiary


import androidx.lifecycle.ViewModel
import com.koenig.gymtracker.models.diaryModel.DiaryManager
import com.koenig.gymtracker.models.diaryModel.DiaryWorkoutModel
import com.koenig.gymtracker.models.exerciseDiaryModel.ExerciseDiaryModel
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutModel

class WorkoutDiaryViewModel : ViewModel() {

    // Is the live data list of workout models
    private lateinit var diaryWorkouts: List<DiaryWorkoutModel>


    // Called when started
    init {
        load()
    }

    // Fills the list from model
    fun load() {
        diaryWorkouts = DiaryManager.findAllDiaryWorkouts()
    }

    fun getAllDiaryExercises(diaryId: Int): List<ExerciseDiaryModel> {
        return diaryWorkouts.find { workout -> diaryId == workout.workoutId }?.exercises!!
    }

    fun getAllDiaryWorkouts(): List<DiaryWorkoutModel> {
        return diaryWorkouts
    }

    // Adds an finished workout to diary
    fun addWorkoutToDiary(workoutModel: DiaryWorkoutModel) {
        DiaryManager.addWorkoutToDiary(workoutModel)
    }

    fun getDiaryWorkoutFromWorkout(
        workoutModel: WorkoutModel,
        exercises: MutableList<ExerciseModel>
    ): DiaryWorkoutModel {
        return DiaryManager.createDiaryWorkoutFromWorkoutModel(workoutModel, exercises)
    }
}