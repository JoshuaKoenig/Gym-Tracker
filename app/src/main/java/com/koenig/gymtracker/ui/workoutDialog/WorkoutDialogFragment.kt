package com.koenig.gymtracker.ui.workoutDialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.koenig.gymtracker.MainActivity
import com.koenig.gymtracker.databinding.FragmentWorkoutDialogBinding
import com.koenig.gymtracker.enums.Modes
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutModel
import com.koenig.gymtracker.ui.workoutManager.WorkoutManagerViewModel
import yuku.ambilwarna.AmbilWarnaDialog


class WorkoutDialogFragment : DialogFragment(), AmbilWarnaDialog.OnAmbilWarnaListener {

    private var mColor = "#FFFFFF"

    // The fragment binding
    private var _fragBinding: FragmentWorkoutDialogBinding? = null
    private val fragBinding get() = _fragBinding!!

    // The passed arguments
    private val args by navArgs<WorkoutDialogFragmentArgs>()

    private val viewModel: WorkoutManagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragBinding = FragmentWorkoutDialogBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        when (args.modes) {
            Modes.ADD -> setUpAddMode()
            Modes.EDIT -> setUpEditMode()
        }

        // Add the workout when button is clicked
        fragBinding.btCheckEditDialogAddWorkout.setOnClickListener {

            when (args.modes) {
                Modes.ADD -> onClickAddWorkout()
                Modes.EDIT -> onClickEditWorkout()
            }

            val action =
                WorkoutDialogFragmentDirections.actionWorkoutDialogFragmentToWorkoutManagerFragment()
            findNavController().navigate(action)
        }

        // Shows the color picker
        fragBinding.btColorPicker.setOnClickListener {
            AmbilWarnaDialog(context, Color.parseColor(mColor), this).show()
        }

        return root
    }

    override fun onCancel(dialog: AmbilWarnaDialog?) {
        // Called when color pick canceled
    }

    override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
        mColor = String.format("#%06X", 0xFFFFFF and color)
        fragBinding.btColorPicker.setBackgroundColor(color)
    }

    // Set up the add mode
    private fun setUpAddMode() {

        (requireActivity() as MainActivity).toolbar.title = "Add Workout"
    }

    // Set up the edit mode
    private fun setUpEditMode() {

        (requireActivity() as MainActivity).toolbar.title = "Edit Workout"
        fragBinding.editWorkoutName.setText(args.workoutModel?.workoutName)
        mColor = args.workoutModel?.workoutColor!!
        fragBinding.btColorPicker.setBackgroundColor(Color.parseColor(mColor))
    }

    // The click function in add mode
    private fun onClickAddWorkout() {
        val exercises = mutableListOf<ExerciseModel>()
        viewModel.addWorkout(
            WorkoutModel(
                fragBinding.editWorkoutName.text.toString(),
                0,
                exercises,
                mColor
            )
        )
    }

    // The click function in edit mode
    private fun onClickEditWorkout() {
        val newTitle = fragBinding.editWorkoutName.text.toString()
        args.workoutModel?.workoutName = newTitle
        args.workoutModel?.workoutColor = mColor
        viewModel.updateWorkout(args.workoutModel!!, args.position)
    }

}

