package com.example.weatherly.data.worker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.weatherly.data.mapper.alertIdMapper
import com.example.weatherly.utils.AlarmHelper
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alertId = intent.getIntExtra("ALERT_ID", -1)

        if (alertId != -1) {
            AlarmHelper.stopAlarm(context)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(alertId)


            val pendingResult = goAsync()
            val entryPoint = EntryPointAccessors.fromApplication(
                context.applicationContext,
                AlarmReceiverEntryPoint::class.java
            )
            val repo = entryPoint.alertRepository()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    repo.updateLastTriggered(alertIdMapper(alertId), System.currentTimeMillis())
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}