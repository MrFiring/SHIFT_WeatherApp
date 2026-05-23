package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                color = if (MaterialTheme.colors.isLight) {
                    MaterialTheme.colors.secondaryVariant
                } else {
                    Color.Unspecified
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.testTag("DrawerButton"),
                onClick = onNavigationClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = if (MaterialTheme.colors.isLight) {
                        MaterialTheme.colors.secondaryVariant
                    } else {
                        MaterialTheme.colors.onPrimary
                    }
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
}