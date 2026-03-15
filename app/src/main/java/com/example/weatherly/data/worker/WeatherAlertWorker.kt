package com.example.weatherly.data.worker

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherly.data.mapper.alertIdMapper
import com.example.weatherly.domain.model.AlertType
import com.example.weatherly.domain.model.NotificationType
import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.domain.repository.AlertRepository
import com.example.weatherly.domain.repository.WeatherRepository
import com.example.weatherly.utils.AlarmHelper
import com.example.weatherly.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class WeatherAlertWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val alertRepository: AlertRepository,
    private val weatherRepository: WeatherRepository
) : CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {

        try {
            val weather = weatherRepository.getCurrentWeather()
            val alerts = alertRepository.getAlerts().first()


            alerts.forEach { alert ->

                if (!alert.isEnabled) return@forEach

                val value = when (alert.alarmType) {

                    AlertType.TEMP.name -> weather?.temperature

                    AlertType.HUMIDITY.name -> weather?.humidity

                    else -> weather?.pressure
                }

                if (value != null) {
                    if (value < alert.start || value > alert.end) {

                        triggerAlert(alert, value)

                    }
                }

            }

            return Result.success()

        } catch (e: Exception) {

            return Result.retry()

        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun triggerAlert(alert: WeatherAlert, value: Int) {
        val notificationId = alertIdMapper(alert.alarmType)
        when (alert.notificationType) {
            NotificationType.NOTIFICATION.name -> {
                NotificationHelper.showNotification(
                    context = applicationContext,
                    notificationId = notificationId,
                    title = "Weather Alert",
                    message = "${alert.alarmType} is $value"
                )
            }

            NotificationType.ALARM.name -> {
                val dismissIntent =
                    Intent(applicationContext, AlarmActionReceiver::class.java).apply {
                        putExtra("ALERT_ID", notificationId)
                    }

                val dismissPendingIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    notificationId,
                    dismissIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                NotificationHelper.showNotification(
                    context = applicationContext,
                    notificationId = notificationId,
                    title = "${alert.alarmType} Alert!",
                    message = "${alert.alarmType} is $value",
                    ongoing = true,
                    fullScreenIntent = true,
                    actions = listOf(
                        NotificationHelper.Action("Dismiss", dismissPendingIntent)
                    )
                )
                AlarmHelper.playAlarm(applicationContext)
            }
        }
    }
}