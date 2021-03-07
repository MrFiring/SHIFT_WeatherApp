package ru.mrfiring.shiftweatherapp.domain.models


data class DomainDrawerMenuItem(
    val drawableId: Int,
    val text: String,
    val onClick: () -> Unit
)
