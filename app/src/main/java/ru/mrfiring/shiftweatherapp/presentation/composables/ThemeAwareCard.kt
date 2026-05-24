package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ThemeAwareCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        backgroundColor = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.background
        } else {
            MaterialTheme.colors.surface
        },
        shape = RectangleShape,
        elevation = 4.dp
    ) {
        content()
    }
}