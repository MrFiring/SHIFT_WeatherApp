package ru.mrfiring.shiftweatherapp.presentation.theme

import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ThemeAwareCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        backgroundColor = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.background
        } else {
            MaterialTheme.colors.surface
        }
    ) {
        content()
    }
}