package com.someguy590.workit.utils

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.someguy590.workit.R
import java.util.UUID

const val CHANNEL_ID = "0"
fun Context.createNotificationChannel() {
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
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

fun Context.createNotification(): Notification {
    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.outline_adb_24)
        .setContentTitle("Title ${UUID.randomUUID()}")
        .setContentText("Content ${UUID.randomUUID()}")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
