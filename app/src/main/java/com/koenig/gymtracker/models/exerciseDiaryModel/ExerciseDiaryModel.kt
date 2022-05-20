package com.koenig.gymtracker.models.exerciseDiaryModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDiaryModel(
    var exerciseName: String = "N/A",
    var weight: Float = 0f,
    var sets: Int = 0,
    var reps: Int = 0
) : Parcelable
