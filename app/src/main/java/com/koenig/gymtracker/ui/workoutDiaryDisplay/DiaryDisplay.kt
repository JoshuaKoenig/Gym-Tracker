package com.koenig.gymtracker.ui.workoutDiaryDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.koenig.gymtracker.MainActivity
import com.koenig.gymtracker.adapters.WorkoutDiaryDisplayAdapter
import com.koenig.gymtracker.adapters.WorkoutDiaryDisplayClickListener
import com.koenig.gymtracker.databinding.FragmentDiaryDisplayBinding
import com.koenig.gymtracker.models.diaryModel.DiaryWorkoutModel
import com.koenig.gymtracker.models.exerciseDiaryModel.ExerciseDiaryModel
import com.koenig.gymtracker.ui.workoutDiary.WorkoutDiaryViewModel


class DiaryDisplay : Fragment(), WorkoutDiaryDisplayClickListener {
    // The fragment binding
    private var _fragBinding: FragmentDiaryDisplayBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val diaryViewModel: WorkoutDiaryViewModel by activityViewModels()

    private val args by navArgs<DiaryDisplayArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment has a options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Binds the fragment to view
        _fragBinding = FragmentDiaryDisplayBinding.inflate(inflater, container, false)
        // The root of fragment layout
        val root = fragBinding.root

        // Binds the recycler view
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        render(diaryViewModel.getAllDiaryWorkouts())

        return root
    }

    // Renders whenever the list has changed
    private fun render(workouts: List<DiaryWorkoutModel>) {
        (requireActivity() as MainActivity).toolbar.title =
            workouts[args.diaryWorkoutId].workoutName
        fragBinding.recyclerView.adapter =
            WorkoutDiaryDisplayAdapter(workouts[args.diaryWorkoutId].exercises, this)

        if (workouts[args.diaryWorkoutId].exercises.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.exerciseNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.exerciseNotFound.visibility = View.GONE
        }
    }

    override fun onExerciseClick(exercise: ExerciseDiaryModel, position: Int) {

    }
}