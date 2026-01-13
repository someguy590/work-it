package com.someguy590.workit.utils

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.someguy590.workit.R
import com.someguy590.workit.Workout
import org.koin.core.annotation.Single
import java.util.UUID

const val CHANNEL_ID = "0"

@Single
class NotificationManager(private val context: Context) {
    fun createNotificationChannel() {
        val name = "WORK_IT"
        val description = "General Work It notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannelCompat.Builder(CHANNEL_ID, importance)
            .setName(name)
            .setDescription(description)
            .setVibrationEnabled(true)
            .build()
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    fun createNotification(): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_adb_24)
            .setContentTitle("Title ${UUID.randomUUID()}")
            .setContentText("Content ${UUID.randomUUID()}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun exerciseNotification(workout: Workout) {
        val n = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_adb_24)
            .setContentTitle(workout.name)
            .setContentText(workout.weight.toString())
            .addAction(R.drawable.check_circle_24px, "Done", null)
            .addAction(R.drawable.blank_circle_24px, "Fail", null)
            .build()

        NotificationManagerCompat.from(context).notify(0, n)
    }
}
