package com.koenig.gymtracker.ui.workoutManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.koenig.gymtracker.adapters.WorkoutClickListener
import com.koenig.gymtracker.adapters.WorkoutManagerAdapter
import com.koenig.gymtracker.databinding.FragmentWorkoutManagerBinding
import com.koenig.gymtracker.enums.Modes
import com.koenig.gymtracker.models.workoutModel.WorkoutModel


class WorkoutManagerFragment : Fragment(), WorkoutClickListener {

    // The fragment binding
    private var _fragBinding: FragmentWorkoutManagerBinding? = null
    private val fragBinding get() = _fragBinding!!

    // The view model binding
    private val workoutManagerViewModel: WorkoutManagerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment has a options menu
        setHasOptionsMenu(true)
        activity?.actionBar?.title = "My Workouts"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binds the fragment to view
        _fragBinding = FragmentWorkoutManagerBinding.inflate(inflater, container, false)
        // The root of fragment layout
        val root = fragBinding.root

        // Binds the recycler view
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Listens to changes of the model, whenever model changes render() is called (Like subscribers)
        workoutManagerViewModel.observableWorkouts.observe(
            viewLifecycleOwner,
            Observer { workouts ->
                workouts?.let { render(workouts) }
            })

        // Initializes the button and show the workout dialog fragment when clicked
        fragBinding.fabAddWorkout.setOnClickListener {

            val action =
                WorkoutManagerFragmentDirections.actionWorkoutManagerFragmentToWorkoutDialogFragment(
                    Modes.ADD
                )
            findNavController().navigate(action)

            // To show an alert dialog
            // WorkoutDialogFragment().show(childFragmentManager, WorkoutDialogFragment.TAG)
        }
        return root
    }

    // Renders whenever the list has changed
    private fun render(workouts: List<WorkoutModel>) {
        fragBinding.recyclerView.adapter = WorkoutManagerAdapter(workouts, this)
        if (workouts.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.workoutsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.workoutsNotFound.visibility = View.GONE
        }
    }

    // Interface from adapter, is called whenever a list item is clicked in adapter
    override fun onWorkoutClick(workout: WorkoutModel) {
        val action =
            WorkoutManagerFragmentDirections.actionWorkoutManagerFragmentToWorkoutDisplayFragment(
                workout.workoutId
            )
        findNavController().navigate(action)
    }

    // Interface from adapter, is called whenever the edit image is clicked
    override fun onWorkoutEditClick(workout: WorkoutModel, position: Int) {
        val action =
            WorkoutManagerFragmentDirections.actionWorkoutManagerFragmentToWorkoutDialogFragment(
                Modes.EDIT,
                position,
                workout
            )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        workoutManagerViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}