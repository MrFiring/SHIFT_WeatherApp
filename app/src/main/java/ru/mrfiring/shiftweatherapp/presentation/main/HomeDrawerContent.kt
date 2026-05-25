package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.mrfiring.shiftweatherapp.domain.models.DrawerMenuItem

@Composable
fun HomeDrawerContent(items: List<DrawerMenuItem>) {
    Column {
        items.forEach {
            DrawerMenuItem(
                painter = painterResource(id = it.drawableId),
                text = it.text,
                onClick = { it.onClick() })
        }
    }
}