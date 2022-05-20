package com.koenig.gymtracker.models.exerciseModel

interface ExerciseStore {

    fun findAll(): List<ExerciseModel>
    fun createExercise(exercise: ExerciseModel)
    fun updateExercise(exercise: ExerciseModel)
}