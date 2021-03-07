package ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem

@Composable
fun HomeDrawerContent(items: List<DomainDrawerMenuItem>) {
    Column {
        items.forEach {
            DrawerMenuItem(
                painter = painterResource(id = it.drawableId),
                text = it.text,
                onClick = { it.onClick() })
        }
    }
}