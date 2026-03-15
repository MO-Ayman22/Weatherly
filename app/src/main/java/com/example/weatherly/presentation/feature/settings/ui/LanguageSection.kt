package com.example.weatherly.presentation.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.home.ui.GlassCard
import com.example.weatherly.utils.AppConstants

@Composable
fun LanguageSection(selectedLang: String, onLangChange: (String) -> Unit) {

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.app_language),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            GlassToggle(
                options = listOf(stringResource(R.string.english), stringResource(R.string.arabic)),
                selectedIndex = if (selectedLang == AppConstants.ENGLISH) 0 else 1,
                onOptionSelected = { index ->
                    val lang = if (index == 0) AppConstants.ENGLISH else AppConstants.ARABIC
                    onLangChange(lang)
                }
            )
        }
    }
}