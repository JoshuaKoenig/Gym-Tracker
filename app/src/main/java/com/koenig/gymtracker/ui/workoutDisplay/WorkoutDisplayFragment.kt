package com.koenig.gymtracker.ui.workoutDisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.koenig.gymtracker.MainActivity
import com.koenig.gymtracker.adapters.ExerciseClickListener
import com.koenig.gymtracker.adapters.WorkoutDisplayAdapter
import com.koenig.gymtracker.databinding.FragmentWorkoutDisplayBinding
import com.koenig.gymtracker.enums.Modes
import com.koenig.gymtracker.models.exerciseModel.ExerciseModel
import com.koenig.gymtracker.models.workoutModel.WorkoutModel
import com.koenig.gymtracker.service.TimerService
import com.koenig.gymtracker.ui.workoutDiary.WorkoutDiaryViewModel
import com.koenig.gymtracker.ui.workoutManager.WorkoutManagerViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WorkoutDisplayFragment : Fragment(), ExerciseClickListener {
    // The fragment binding
    private var _fragBinding: FragmentWorkoutDisplayBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val viewModel: WorkoutManagerViewModel by activityViewModels()

    private val diaryViewModel: WorkoutDiaryViewModel by activityViewModels()

    private lateinit var serviceIntent: Intent
    private var time = 0.0

    private val args by navArgs<WorkoutDisplayFragmentArgs>()

    private lateinit var currentWorkout: WorkoutModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment has a options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        serviceIntent = Intent(container?.context, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        // Binds the fragment to view
        _fragBinding = FragmentWorkoutDisplayBinding.inflate(inflater, container, false)
        // The root of fragment layout
        val root = fragBinding.root

        // Binds the recycler view
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Get the view model
        // viewModel = ViewModelProvider(this).get(WorkoutManagerViewModel::class.java)

        currentWorkout = viewModel.getCurrentWorkout(args.workoutId)

        // Listens to changes of the model, whenever model changes render() is called (Like subscriber)
        viewModel.observableWorkouts.observe(viewLifecycleOwner, Observer { workouts ->
            workouts?.let { render(workouts) }
        })


        // Navigates to exercise dialog fragment passing the current workout id
        fragBinding.fabAddExercise.setOnClickListener {
            val action =
                WorkoutDisplayFragmentDirections.actionWorkoutDisplayFragmentToExerciseDialogFragment(
                    args.workoutId,
                    Modes.ADD,
                    0
                )

            findNavController().navigate(action)
        }

        // Starts the workout
        fragBinding.fabStartWorkout.setOnClickListener {
            startTimer()
        }

        // Finishes the workout
        fragBinding.fabFinishWorkout.setOnClickListener {
            stopTimer()

            // Add finished workout to diary
            val currentDiaryWorkout =
                diaryViewModel.getDiaryWorkoutFromWorkout(currentWorkout, currentWorkout.exercises)
            diaryViewModel.addWorkoutToDiary(currentDiaryWorkout)
        }

        return root
    }

    private fun stopTimer() {
        currentWorkout.time = getTimeStringFromDouble(time)
        currentWorkout.date = SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(Date())
        requireActivity().stopService(serviceIntent)
        time = 0.0
        fragBinding.editTextTime.text = getTimeStringFromDouble(time)

        // TODO
        fragBinding.fabStartWorkout.visibility = View.VISIBLE
        fragBinding.fabFinishWorkout.visibility = View.INVISIBLE

    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)

        // TODO
        fragBinding.fabStartWorkout.visibility = View.INVISIBLE
        fragBinding.fabFinishWorkout.visibility = View.VISIBLE
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            println(time)
            fragBinding.editTextTime.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)


    // Renders whenever the list has changed
    private fun render(workout: List<WorkoutModel>) {
        (requireActivity() as MainActivity).toolbar.title = workout[args.workoutId].workoutName
        fragBinding.recyclerView.adapter =
            WorkoutDisplayAdapter(workout[args.workoutId].exercises, this)
        if (workout[args.workoutId].exercises.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.exerciseNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.exerciseNotFound.visibility = View.GONE
        }
    }

    // Called when exercise item is clicked
    override fun onExerciseClick(exercise: ExerciseModel, exercisePosition: Int) {
        val action =
            WorkoutDisplayFragmentDirections.actionWorkoutDisplayFragmentToExerciseDialogFragment(
                args.workoutId,
                Modes.EDIT,
                exercisePosition,
                exercise
            )
        findNavController().navigate(action)
    }
}