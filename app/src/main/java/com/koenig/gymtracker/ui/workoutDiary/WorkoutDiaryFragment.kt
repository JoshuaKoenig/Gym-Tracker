package com.koenig.gymtracker.ui.workoutDiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.koenig.gymtracker.MainActivity
import com.koenig.gymtracker.adapters.WorkoutDiaryAdapter
import com.koenig.gymtracker.adapters.WorkoutDiaryClickListener
import com.koenig.gymtracker.databinding.FragmentWorkoutDiaryBinding
import com.koenig.gymtracker.models.diaryModel.DiaryWorkoutModel

class WorkoutDiaryFragment : Fragment(), WorkoutDiaryClickListener {

    // The fragment binding
    private var _fragBinding: FragmentWorkoutDiaryBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val diaryViewModel: WorkoutDiaryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment has a options menu
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).toolbar.title = "My Diary"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Binds the fragment to view
        _fragBinding = FragmentWorkoutDiaryBinding.inflate(inflater, container, false)
        // The root of fragment layout
        val root = fragBinding.root

        // Binds the recycler view
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Listens to changes of the model, whenever model changes render() is called (Like subscribers)
        render(diaryViewModel.getAllDiaryWorkouts())

        return root
    }

    // Renders whenever the list has changed
    private fun render(diaryWorkouts: List<DiaryWorkoutModel>) {
        (requireActivity() as MainActivity).toolbar.title = "My Diary"
        fragBinding.recyclerView.adapter = WorkoutDiaryAdapter(diaryWorkouts, this)
        if (diaryWorkouts.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.workoutDiaryNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.workoutDiaryNotFound.visibility = View.GONE
        }
    }

    override fun onWorkoutClick(workout: DiaryWorkoutModel) {
        val action =
            WorkoutDiaryFragmentDirections.actionWorkoutDiaryFragmentToDiaryDisplay(workout.workoutId)
        findNavController().navigate(action)
    }

}