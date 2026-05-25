package ru.mrfiring.shiftweatherapp.domain.models


data class DrawerMenuItem(
    val drawableId: Int,
    val text: String,
    val onClick: () -> Unit
)
