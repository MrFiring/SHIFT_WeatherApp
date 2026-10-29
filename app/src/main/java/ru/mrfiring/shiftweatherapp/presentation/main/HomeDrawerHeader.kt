package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun HomeDrawerHeader() {
    Text(
        text = stringResource(R.string.drawer_title),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
    )
}
