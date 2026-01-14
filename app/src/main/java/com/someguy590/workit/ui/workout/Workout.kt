package com.someguy590.workit.ui.workout

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.someguy590.workit.R
import com.someguy590.workit.ui.theme.WorkItTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val workoutState by workoutViewModel.workoutUIState.collectAsStateWithLifecycle()
    WorkoutContent(
        workoutState,
        workoutViewModel::addWorkout,
        workoutViewModel::editWorkout,
        workoutViewModel::toggleEditMode,
        workoutViewModel::deleteWorkout,
        modifier
    )
}

@Composable
private fun WorkoutContent(
    workoutState: WorkoutState,
    handleAddWorkout: () -> Unit,
    handleEditWorkout: (Int, Long, String, String, Long) -> Unit,
    handleToggleEditMode: () -> Unit,
    handleDeleteWorkout: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(workoutState.workouts, { i, workout -> workout.id }) { i, workout ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                if (workoutState.isInEditMode) {
                    OutlinedIconButton({ handleDeleteWorkout(i) }) {
                        Icon(painterResource(R.drawable.close_24px), "Delete workout")
                    }
                }
                OutlinedCard(
                    modifier = Modifier.combinedClickable(
                        onLongClick = handleToggleEditMode,
                        onClick = {}
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Name:")
                            OutlinedTextField(
                                workout.name,
                                {
                                    handleEditWorkout(
                                        i,
                                        workout.id,
                                        it,
                                        workout.weight,
                                        workout.sets
                                    )
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Weight:")
                            OutlinedTextField(
                                workout.weight,
                                {
                                    handleEditWorkout(
                                        i,
                                        workout.id,
                                        workout.name,
                                        it,
                                        workout.sets
                                    )
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillParentMaxWidth()
                        ) {
                            val setsDone = rememberSaveable {
                                mutableStateListOf(*Array(workout.sets.toInt()) { false })
                            }
                            for ((i, setSetDone) in setsDone.withIndex()) {
                                IconToggleButton(setSetDone, { setsDone[i] = it }) {
                                    Icon(
                                        painterResource(
                                            if (setSetDone) R.drawable.check_circle_24px
                                            else R.drawable.blank_circle_24px
                                        ),
                                        "Set status"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        item {
            IconButton(handleAddWorkout) {
                Text("+")
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WorkoutScreenPreview(@PreviewParameter(WorkoutPreviewParameter::class) workoutState: WorkoutState) {
    WorkItTheme {
        Scaffold { innerPadding ->
            WorkoutContent(
                workoutState,
                {},
                { _, _, _, _, _ -> },
                {},
                {},
                Modifier.padding(innerPadding)
            )
        }
    }
}
