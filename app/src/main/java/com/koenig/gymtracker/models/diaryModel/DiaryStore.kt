package com.koenig.gymtracker.models.diaryModel

import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutModel

interface DiaryStore {

    fun findAllDiaryWorkouts(): List<DiaryWorkoutModel>
    fun addWorkoutToDiary(workout: DiaryWorkoutModel)
    fun createDiaryWorkoutFromWorkoutModel(
        workout: WorkoutModel,
        exerciseModel: MutableList<ExerciseModel>
    ): DiaryWorkoutModel
}