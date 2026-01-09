package com.someguy590.workit.ui.workout

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.someguy590.workit.Workout

class WorkoutPreviewParameter : PreviewParameterProvider<WorkoutState> {
    override val values = sequenceOf(
        WorkoutState(),
        WorkoutState(
            listOf(
                Workout(1, "Bench Press", 45.0),
                Workout(2, "Gorilla Press", 25.5),
                Workout(3, "Squats", 105.5)
            )
        ),
        WorkoutState(
            listOf(
                Workout(1, "Bench Press", 45.0),
                Workout(2, "Gorilla Press", 25.5),
                Workout(3, "Squats", 105.5)
            ),
            isInEditMode = true
        )
    )
}
