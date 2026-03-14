package com.example.weatherly.data.mapper

import com.example.weatherly.R

fun iconMapper(icon: String?): Int {
    return when (icon) {
        "01d", "01n" -> R.drawable.icon_01d
        "02d", "02n" -> R.drawable.icon_02d
        "03d", "03n" -> R.drawable.icon_03d
        "04d", "04n" -> R.drawable.icon_04d
        "09d", "09n" -> R.drawable.icon_09d
        "10d", "10n" -> R.drawable.icon_10d
        "11d", "11n" -> R.drawable.icon_11d
        "13d", "13n" -> R.drawable.icon_13d
        "50d", "50n" -> R.drawable.icon_50d
        else -> R.drawable.icon_01d
    }
}