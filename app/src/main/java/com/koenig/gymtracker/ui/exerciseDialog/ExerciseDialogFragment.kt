package com.koenig.gymtracker.ui.exerciseDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.koenig.gymtracker.MainActivity
import com.koenig.gymtracker.databinding.FragmentExerciseDialogBinding
import com.koenig.gymtracker.enums.Modes
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.ui.workoutManager.WorkoutManagerViewModel


class ExerciseDialogFragment : DialogFragment() {

    private var _fragBinding: FragmentExerciseDialogBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val viewModel: WorkoutManagerViewModel by activityViewModels()

    // The passed arguments
    private val args by navArgs<ExerciseDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragBinding = FragmentExerciseDialogBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        // Set up the current mode
        when (args.modes) {
            Modes.ADD -> setUpAddMode()
            Modes.EDIT -> setUpEditMode()
        }

        // Add or Edit the exercise when button is clicked
        fragBinding.btCheckEditDialogAdd.setOnClickListener {
            when (args.modes) {
                Modes.ADD -> onClickAddExercise()
                Modes.EDIT -> onClickUpdateExercise()
            }

            findNavController().navigateUp()

        }
        return root
    }

    // Sets up the add mode
    private fun setUpAddMode() {
        (requireActivity() as MainActivity).toolbar.title = "Add Exercise"

    }

    // Sets up the edit mode
    private fun setUpEditMode() {
        (requireActivity() as MainActivity).toolbar.title = "Track Progress"

        fragBinding.editExerciseTitle.setText(args.exercise!!.exerciseName)
        fragBinding.editExerciseKg.hint = args.exercise!!.weight.toString()
        fragBinding.editExerciseSets.hint = args.exercise!!.sets.toString()
        fragBinding.editExerciseReps.hint = args.exercise!!.reps.toString()
    }

    // Adds new exercise to workout
    private fun onClickAddExercise() {
        // Validation if texts are blank => Set text to 0 if blank
        if (fragBinding.editExerciseKg.text.toString().trim().isBlank())
            fragBinding.editExerciseKg.setText("0")

        if (fragBinding.editExerciseSets.text.toString().trim().isBlank())
            fragBinding.editExerciseSets.setText("0")

        if (fragBinding.editExerciseReps.text.toString().trim().isBlank())
            fragBinding.editExerciseReps.setText("0")

        val exercise = ExerciseModel(
            fragBinding.editExerciseTitle.text.toString(),
            fragBinding.editExerciseKg.text.toString().toFloat(),
            fragBinding.editExerciseSets.text.toString().toInt(),
            fragBinding.editExerciseReps.text.toString().toInt()
        )

        // Add the exercise
        viewModel.addExercise(exercise, args.workoutId)
    }

    // Updates the current exercise
    private fun onClickUpdateExercise() {
        // Validation whether new value was entered or not
        val newWeightValue: Float =
            if (fragBinding.editExerciseKg.text.toString().trim()
                    .isBlank()
            ) fragBinding.editExerciseKg.hint.toString().toFloat()
            else fragBinding.editExerciseKg.text.toString().toFloat()

        val newSetsValue: Int =
            if (fragBinding.editExerciseSets.text.toString().trim()
                    .isBlank()
            ) fragBinding.editExerciseSets.hint.toString().toInt()
            else fragBinding.editExerciseSets.text.toString().toInt()

        val newRepsValue: Int =
            if (fragBinding.editExerciseReps.text.toString().trim()
                    .isBlank()
            ) fragBinding.editExerciseReps.hint.toString().toInt()
            else fragBinding.editExerciseReps.text.toString().toInt()

        val updatedExercise = ExerciseModel(
            fragBinding.editExerciseTitle.text.toString(),
            newWeightValue,
            newSetsValue,
            newRepsValue
        )

        // Update the exercise
        viewModel.updateExercise(updatedExercise, args.workoutId, args.exercisePosition)
    }
}