package com.someguy590.workit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.someguy590.workit.ui.theme.WorkItTheme
import com.someguy590.workit.ui.workout.WorkoutScreen
import com.someguy590.workit.utils.NotificationManager
import org.koin.mp.KoinPlatform

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = KoinPlatform.getKoin().get<NotificationManager>()
        notificationManager.createNotificationChannel()

        enableEdgeToEdge()
        setContent {
            WorkItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkoutScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
