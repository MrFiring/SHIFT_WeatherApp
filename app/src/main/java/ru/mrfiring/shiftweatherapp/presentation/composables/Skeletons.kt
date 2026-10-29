package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

private const val SKELETON_MIN_ALPHA = 0.55f
private const val SKELETON_MAX_ALPHA = 1f
private const val SKELETON_PULSE_DURATION_MILLIS = 700

@Composable
fun CityItemSkeleton(modifier: Modifier = Modifier) {
    // Альфа читается внутри graphicsLayer-ламбды — в фазе отрисовки,
    // поэтому пульсация не вызывает рекомпозиций на каждом кадре.
    val pulseAlpha = rememberSkeletonPulseAlpha()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = pulseAlpha.value }
            .padding(horizontal = 6.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SkeletonCircle(size = 40.dp)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkeletonBar(widthFraction = 0.62f, height = 14.dp)

            SkeletonBar(widthFraction = 0.44f, height = 10.dp)
        }

        SkeletonCircle(size = 20.dp)
    }
}

@Composable
fun CountryTileSkeleton(modifier: Modifier = Modifier) {
    val pulseAlpha = rememberSkeletonPulseAlpha()

    Box(
        modifier = modifier
            .aspectRatio(128f / 100f)
            .graphicsLayer { alpha = pulseAlpha.value }
            .background(
                color = MaterialTheme.extendedColors.surfaceContainerHigh,
                shape = RoundedCornerShape(12.dp)
            )
    )
}

@Composable
private fun SkeletonCircle(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = MaterialTheme.extendedColors.surfaceContainerHigh,
                shape = CircleShape
            )
    )
}

@Composable
private fun SkeletonBar(widthFraction: Float, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .height(height)
            .background(
                color = MaterialTheme.extendedColors.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

@Composable
private fun rememberSkeletonPulseAlpha(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "skeletonPulse")

    return infiniteTransition.animateFloat(
        initialValue = SKELETON_MIN_ALPHA,
        targetValue = SKELETON_MAX_ALPHA,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SKELETON_PULSE_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        ),
        label = "skeletonPulseAlpha"
    )
}
