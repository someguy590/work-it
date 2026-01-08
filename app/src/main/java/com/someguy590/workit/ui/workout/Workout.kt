package com.someguy590.workit.ui.workout

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        modifier
    )
}

@Composable
private fun WorkoutContent(
    workoutState: WorkoutState,
    handleAddWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        items(workoutState.workouts, { it.id }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Name:")
                OutlinedTextField(it.name, {})
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Weight:")
                OutlinedTextField(it.weight.toString(), {})
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
                Modifier.padding(innerPadding)
            )
        }
    }
}
