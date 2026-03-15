package com.example.weatherly.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.weatherly.R

object AlarmHelper {
    private var mediaPlayer: MediaPlayer? = null

    fun playAlarm(context: Context) {
        stopAlarm(context)
        mediaPlayer = MediaPlayer.create(context, R.raw.funny_alarm)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

    }

    fun stopAlarm(context: Context) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}