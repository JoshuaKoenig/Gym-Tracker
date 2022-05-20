package com.koenig.gymtracker.models.diaryModel

import android.os.Parcelable
import com.koenig.gymtracker.models.exerciseDiaryModel.ExerciseDiaryModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryWorkoutModel(
    var workoutName: String = "N/A",
    var workoutId: Int = 0,
    var exercises: List<ExerciseDiaryModel>,
    var workoutColor: String = "N/A",
    var time: String = "00.00.00",
    var date: String = "dd-mm-yyyy"
) : Parcelable
