package com.example.weatherly.presentation.feature.alerts.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherly.R
import com.example.weatherly.domain.model.AlertType
import com.example.weatherly.domain.model.NotificationType
import com.example.weatherly.domain.model.WeatherAlert
import com.example.weatherly.presentation.feature.home.ui.GlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertItem(
    modifier: Modifier = Modifier,
    alert: WeatherAlert,
    onToggle: (WeatherAlert, Boolean) -> Unit,
    onApply: (WeatherAlert) -> Unit
) {

    var isEnabled by remember { mutableStateOf(alert.isEnabled) }
    var range by remember { mutableStateOf(alert.start..alert.end) }
    var notifyBefore by remember { mutableStateOf("${alert.notifyBeforeMinutes}") }
    var notificationType by remember { mutableStateOf(alert.notificationType) }
    val typeMeta = when (alert.alarmType) {
        AlertType.TEMP.name -> "🌡" to stringResource(R.string.temperature_alert)
        AlertType.HUMIDITY.name -> "💧" to stringResource(R.string.humidity_alert)
        else -> "📈" to stringResource(R.string.pressure_alert)
    }

    val icon = typeMeta.first
    val label = typeMeta.second
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(icon, fontSize = 22.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            label,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Text(
                            if (isEnabled) stringResource(R.string.tap_to_adjust_settings) else stringResource(
                                R.string.disabled
                            ),
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                Switch(
                    checked = isEnabled,
                    onCheckedChange = {
                        isEnabled = it
                        alert.isEnabled = isEnabled
                        onToggle(alert, isEnabled)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF64B5F6),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.25f)
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(
                visible = isEnabled,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {

                    Text(
                        stringResource(
                            R.string.threshold_range,
                            range.start.toInt(),
                            range.endInclusive.toInt()
                        ),
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.height(8.dp))

                    RangeSlider(
                        value = range,
                        onValueChange = {
                            range = it
                            alert.start = it.start
                            alert.end = it.endInclusive

                        },
                        valueRange = alert.min..alert.max,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF64B5F6),
                            activeTrackColor = Color(0xFF64B5F6),
                            inactiveTrackColor = Color.White.copy(alpha = 0.25f)
                        )
                    )

                    Spacer(Modifier.height(16.dp))


                    Text(
                        stringResource(R.string.notify_before),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))

                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = notifyBefore + " " + stringResource(R.string.minutes),
                            onValueChange = {
                                alert.notifyBeforeMinutes = it.split(" ")[0].toInt()
                            },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color.White.copy(alpha = 0.5f),
                                unfocusedContainerColor = Color.White.copy(alpha = 0.25f),
                                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                                focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            ),
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.clickable { expanded = true }
                                )
                            },
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf(
                                "5 " + stringResource(R.string.minutes),
                                "10 " + stringResource(R.string.minutes),
                                "15 " + stringResource(R.string.minutes),
                                "20" + stringResource(R.string.minutes),
                                "25" + stringResource(R.string.minutes),
                                "30" + stringResource(R.string.minutes),

                            )
                                .forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            notifyBefore = option
                                            alert.notifyBeforeMinutes = option.split(" ")[0].toInt()
                                            expanded = false
                                        }
                                    )
                                }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    /** Notification Type **/
                    Text(
                        stringResource(R.string.notification_type),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = notificationType == NotificationType.NOTIFICATION.name,
                            onClick = {
                                notificationType = NotificationType.NOTIFICATION.name
                                alert.notificationType = NotificationType.NOTIFICATION.name
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF64B5F6),
                                unselectedColor = Color.White.copy(alpha = 0.5f)
                            )
                        )
                        Text(
                            stringResource(R.string.notification),
                            Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = notificationType == NotificationType.ALARM.name,
                            onClick = {
                                notificationType = NotificationType.ALARM.name
                                alert.notificationType = NotificationType.ALARM.name
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF64B5F6),
                                unselectedColor = Color.White.copy(alpha = 0.5f)
                            )
                        )
                        Text(
                            stringResource(R.string.alarm),
                            Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { onApply(alert) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF64B5F6),
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.apply), color = Color.White)
                    }
                }
            }
        }
    }
}
