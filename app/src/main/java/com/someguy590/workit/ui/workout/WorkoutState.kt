package com.someguy590.workit.ui.workout

import com.someguy590.workit.Workout

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val isInEditMode: Boolean = false
)
