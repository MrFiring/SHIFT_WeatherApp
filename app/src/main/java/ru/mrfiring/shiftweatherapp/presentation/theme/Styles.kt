package ru.mrfiring.shiftweatherapp.presentation.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.presentation.composables.ThemeAwareCard

@Composable
fun CardWithPaddingAndFillWidth(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(8.dp),
    content: @Composable () -> Unit
) {
    ThemeAwareCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        content()
    }
}