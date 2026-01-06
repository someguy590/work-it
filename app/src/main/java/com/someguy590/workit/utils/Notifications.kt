package com.someguy590.workit.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationManagerCompat

const val CHANNEL_ID = "0"
fun Context.createNotificationChannel() {
    val name = "WORK_IT"
    val description = "General Work It notifications"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(CHANNEL_ID, name, importance)
    channel.description = description
    // Register the channel with the system. You can't change the importance
    // or other notification behaviors after this.
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}
