package com.koenig.gymtracker.models.workoutModel

import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    var workoutName: String = "N/A",
    var workoutId: Int = 0,
    var exercises: MutableList<ExerciseModel>,
    var workoutColor: String = "N/A",
    var time: String = "00.00.00",
    var isStarted: Boolean = false,
    var date: String = "dd-mm-yyyy"
) : Parcelable