package com.koenig.gymtracker.models.diaryModel

import com.koenig.gymtracker.models.exerciseDiaryModel.ExerciseDiaryModel
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutModel

object DiaryManager : DiaryStore {
    var lastId = 0

    private fun getId(): Int {
        return lastId++
    }

    private val diaryWorkouts = ArrayList<DiaryWorkoutModel>()

    override fun findAllDiaryWorkouts(): List<DiaryWorkoutModel> {
        return diaryWorkouts
    }

    override fun addWorkoutToDiary(workout: DiaryWorkoutModel) {
        diaryWorkouts.add(workout)
    }

    override fun createDiaryWorkoutFromWorkoutModel(
        workout: WorkoutModel,
        exerciseModel: MutableList<ExerciseModel>
    ): DiaryWorkoutModel {

        val newExercises = mutableListOf<ExerciseDiaryModel>()

        exerciseModel.forEach {

            newExercises.add(
                ExerciseDiaryModel(
                    it.exerciseName,
                    it.weight,
                    it.sets,
                    it.reps
                )
            )
        }

        return DiaryWorkoutModel(
            workout.workoutName,
            getId(),
            newExercises,
            workout.workoutColor,
            workout.time,
            workout.date
        )
    }
}