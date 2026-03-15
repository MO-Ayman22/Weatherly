package com.example.weatherly.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.main.ui.MainActivity

object NotificationHelper {

    data class Action(val title: String, val pendingIntent: PendingIntent)

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @SuppressLint("FullScreenIntentPolicy")
    fun showNotification(
        context: Context,
        title: String,
        message: String,
        ongoing: Boolean = false,
        fullScreenIntent: Boolean = false,
        actions: List<Action> = emptyList(),
        notificationId: Int
    ) {
        val channelId = "weather_alert_channel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_11d)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(ongoing)
            .setAutoCancel(!ongoing)

        if (fullScreenIntent) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            notificationBuilder.setFullScreenIntent(pendingIntent, true)
        }

        actions.forEach { action ->
            notificationBuilder.addAction(0, action.title, action.pendingIntent)
        }

        NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder.build())
    }
}