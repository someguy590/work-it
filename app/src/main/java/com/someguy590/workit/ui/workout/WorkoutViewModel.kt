package com.someguy590.workit.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.someguy590.workit.DB
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
        viewModelScope.launch {
            db.workoutQueries.selectAll().asFlow().mapToList(Dispatchers.IO).collect {
                workouts ->
                workoutState.update {
                    it.copy(workouts = workouts)
                }
            }
        }
    }

    fun addWorkout() {
        val workoutQueries = db.workoutQueries
        viewModelScope.launch(Dispatchers.IO) {
            workoutQueries.insert("", 0.0)
        }
    }
}
