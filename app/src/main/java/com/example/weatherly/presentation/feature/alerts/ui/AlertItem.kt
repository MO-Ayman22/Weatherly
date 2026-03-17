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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
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
    isEditable: Boolean,
    onToggle: (WeatherAlert, Boolean) -> Unit,
    onApply: (WeatherAlert) -> Unit
) {
    val activeColor = Color(0xFFFFE082)
    val trackColor = Color.White.copy(alpha = 0.3f)

    var isEnabled by remember { mutableStateOf(alert.isEnabled) }
    var range by remember { mutableStateOf(alert.start..alert.end) }
    var notifyBefore by remember { mutableStateOf("${alert.notifyBeforeMinutes}") }
    var notificationType by remember { mutableStateOf(alert.notificationType) }

    val typeMeta = when (alert.alarmType) {
        AlertType.TEMP.name -> "🌡" to stringResource(R.string.temperature_alert)
        AlertType.HUMIDITY.name -> "💧" to stringResource(R.string.humidity_alert)
        else -> "📈" to stringResource(R.string.pressure_alert)
    }

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .alpha(if (isEditable) 1f else 0.6f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(typeMeta.first, fontSize = 24.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            typeMeta.second,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.White
                        )
                        Text(
                            if (!isEditable) stringResource(R.string.permissions_required_msg)
                            else if (isEnabled) stringResource(R.string.tap_to_adjust_settings)
                            else stringResource(R.string.disabled),
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }

                Switch(
                    checked = isEnabled && isEditable,
                    onCheckedChange = {
                        isEnabled = it
                        onToggle(alert, it)
                    },
                    enabled = isEditable,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = activeColor.copy(alpha = 0.8f),
                        uncheckedThumbColor = Color.LightGray,
                        uncheckedTrackColor = trackColor,
                        disabledCheckedTrackColor = activeColor.copy(alpha = 0.2f),
                        disabledUncheckedTrackColor = trackColor.copy(alpha = 0.1f)
                    )
                )
            }

            AnimatedVisibility(
                visible = isEnabled && isEditable,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        stringResource(
                            R.string.threshold_range,
                            range.start.toInt(),
                            range.endInclusive.toInt()
                        ),
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    RangeSlider(
                        value = range,
                        onValueChange = { range = it },
                        valueRange = alert.min..alert.max,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = activeColor,
                            inactiveTrackColor = trackColor
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    var expanded by remember { mutableStateOf(false) }
                    Text(
                        stringResource(R.string.notify_before),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Box(modifier = Modifier.padding(vertical = 8.dp)) {
                        OutlinedTextField(
                            value = "$notifyBefore " + stringResource(R.string.minutes),
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.clickable { expanded = true }
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White.copy(alpha = 0.1f),
                                unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedIndicatorColor = activeColor,
                                unfocusedIndicatorColor = Color.White.copy(alpha = 0.2f)
                            )
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        NotificationOption(
                            selected = notificationType == NotificationType.NOTIFICATION.name,
                            label = stringResource(R.string.notification),
                            activeColor = activeColor,
                            onClick = { notificationType = NotificationType.NOTIFICATION.name }
                        )
                        NotificationOption(
                            selected = notificationType == NotificationType.ALARM.name,
                            label = stringResource(R.string.alarm),
                            activeColor = activeColor,
                            onClick = { notificationType = NotificationType.ALARM.name }
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val notifyVal =
                                notifyBefore.filter { it.isDigit() }.ifEmpty { "0" }.toInt()
                            alert.apply {
                                start = range.start
                                end = range.endInclusive
                                notifyBeforeMinutes = notifyVal
                                this.notificationType = notificationType
                            }
                            onApply(alert)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = activeColor,
                            contentColor = Color(0xFF5D4037)
                        )
                    ) {
                        Text(stringResource(R.string.apply), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationOption(selected: Boolean, label: String, activeColor: Color, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = activeColor,
                unselectedColor = Color.White.copy(alpha = 0.5f)
            )
        )
        Text(label, color = Color.White, fontSize = 14.sp)
    }
}
