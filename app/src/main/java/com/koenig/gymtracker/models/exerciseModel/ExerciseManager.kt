package com.koenig.gymtracker.models.exerciseModel

object ExerciseManager : ExerciseStore {

    private val exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel> {
        return exercises
    }

    override fun createExercise(exercise: ExerciseModel) {

    }

    override fun updateExercise(exercise: ExerciseModel) {

    }

}