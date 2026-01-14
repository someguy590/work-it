package com.someguy590.workit.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.someguy590.workit.DB
import com.someguy590.workit.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class WorkoutViewModel(private val db: DB) : ViewModel() {
    private val workoutState = MutableStateFlow(WorkoutState())
    val workoutUIState = workoutState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            workoutState.update {
                it.copy(workouts = db.workoutQueries.selectAll().executeAsList())
            }
        }
    }

    fun addWorkout() {
        val workoutQueries = db.workoutQueries
        viewModelScope.launch(Dispatchers.IO) {
            workoutQueries.insert("", "0.0", 5)
            val id = workoutQueries.lastInsertRowId().executeAsOne()
            val workout = Workout(id, "", "0.0", 5)
            workoutState.update {
                it.copy(workouts = it.workouts + workout)
            }
        }
    }

    fun editWorkout(i: Int, id: Long, name: String, weight: String, reps: Long) {
        workoutState.update {
            val workout = Workout(id, name, weight, reps)
            viewModelScope.launch(Dispatchers.IO) {
                db.workoutQueries.update(workout.name, workout.weight, workout.sets, workout.id)
            }
            val workouts = it.workouts.toMutableList()
            workouts[i] = Workout(id, name, weight, reps)
            it.copy(workouts = workouts)
        }
    }

    fun toggleEditMode() {
        workoutState.update { it.copy(isInEditMode = !it.isInEditMode) }
    }

    fun deleteWorkout(i: Int) {
        workoutState.update {
            val workout = it.workouts[i]
            viewModelScope.launch(Dispatchers.IO) {
                db.workoutQueries.delete(workout.id)
            }
            it.copy(workouts = it.workouts - workout)
        }
    }
}
