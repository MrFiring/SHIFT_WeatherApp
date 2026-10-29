package ru.mrfiring.shiftweatherapp.presentation.onboarding

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
    onboardingComponent: OnboardingComponent,
) {
    val locationPermissionState = rememberPermissionState(
        permission = ACCESS_COARSE_LOCATION,
        onPermissionResult = { granted -> onboardingComponent.handleLocationRequestResult(granted) }
    )
    val state by onboardingComponent.state.subscribeAsState()

    DisposableEffect(locationPermissionState.status.isGranted) {
        onboardingComponent.handleInitialLocationGranted(locationPermissionState.status.isGranted)
        onDispose { }
    }

    OnboardingContent(
        page = state.page,
        locationGranted = state.locationGranted,
        onAllowAccessClick = { locationPermissionState.launchPermissionRequest() },
        onNextClick = onboardingComponent::goNext,
        onSkipClick = onboardingComponent::skipRequested,
        onStartClick = onboardingComponent::startRequested,
        onPageSelected = onboardingComponent::selectPage
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingContent(
    page: OnboardingPage,
    locationGranted: Boolean,
    onAllowAccessClick: () -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    onStartClick: () -> Unit,
    onPageSelected: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = page.pageIndex,
        pageCount = { OnboardingPage.entries.size }
    )

    LaunchedEffect(page) {
        if (!pagerState.isScrollInProgress && pagerState.settledPage != page.pageIndex) {
            pagerState.animateScrollToPage(page.pageIndex)
        }
    }

    LaunchedEffect(pagerState.settledPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress && pagerState.settledPage != page.pageIndex) {
            onPageSelected(pagerState.settledPage)
        }
    }

    val currentPagerPage = OnboardingPage.entries.getOrElse(pagerState.currentPage) { OnboardingPage.WELCOME }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                OnboardingPage(
                    page = OnboardingPage.entries.getOrElse(pageIndex) { OnboardingPage.WELCOME },
                    locationGranted = locationGranted
                )
            }

            OnboardingPageIndicator(
                pageCount = OnboardingPage.entries.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = when (currentPagerPage) {
                        OnboardingPage.PERMISSION -> onAllowAccessClick
                        OnboardingPage.DONE -> onStartClick
                        else -> onNextClick
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CircleShape
                ) {
                    Text(
                        stringResource(
                            when (currentPagerPage) {
                                OnboardingPage.PERMISSION -> R.string.onboarding_allow
                                OnboardingPage.DONE -> R.string.onboarding_start
                                else -> R.string.onboarding_next
                            }
                        )
                    )
                }

                if (currentPagerPage == OnboardingPage.PERMISSION) {
                    TextButton(
                        onClick = onSkipClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.onboarding_skip),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    page: OnboardingPage,
    locationGranted: Boolean,
    modifier: Modifier = Modifier
) {
    val extendedColors = MaterialTheme.extendedColors
    val sunIconTint = if (isSystemInDarkTheme()) extendedColors.clear else extendedColors.clearInk

    val content = when (page) {
        OnboardingPage.WELCOME -> OnboardingPageContent(
            icon = Icons.Rounded.WbSunny,
            iconTint = sunIconTint,
            kickerTextRes = R.string.onboarding_kicker_app,
            titleTextRes = R.string.onboarding_title_welcome,
            bodyTextRes = R.string.onboarding_text_welcome
        )

        OnboardingPage.CITIES -> OnboardingPageContent(
            icon = Icons.Rounded.Public,
            iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
            kickerTextRes = R.string.onboarding_kicker_app,
            titleTextRes = R.string.onboarding_title_cities,
            bodyTextRes = R.string.onboarding_text_cities
        )

        OnboardingPage.PERMISSION -> OnboardingPageContent(
            icon = Icons.Rounded.LocationOn,
            iconTint = extendedColors.primaryStrong,
            kickerTextRes = R.string.onboarding_kicker_permission,
            titleTextRes = R.string.onboarding_title_permission,
            bodyTextRes = R.string.onboarding_text_permission
        )

        OnboardingPage.DONE -> OnboardingPageContent(
            icon = Icons.Rounded.CheckCircle,
            iconTint = extendedColors.ok,
            kickerTextRes = R.string.onboarding_kicker_app,
            titleTextRes = R.string.onboarding_title_done,
            bodyTextRes = if (locationGranted) {
                R.string.onboarding_text_done_granted
            } else {
                R.string.onboarding_text_done_skipped
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = content.icon,
            contentDescription = null,
            tint = content.iconTint,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(content.kickerTextRes).uppercase(),
            fontSize = 12.sp,
            letterSpacing = 0.14.em,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(content.titleTextRes),
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(content.bodyTextRes),
            fontSize = 14.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 280.dp)
        )
    }
}

@Composable
private fun OnboardingPageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    val dotSize = 6.dp
    val dotSpacing = 12.dp
    val pillWidth = 18.dp
    val slotStride = dotSize + dotSpacing
    val dotInset = (pillWidth - dotSize) / 2

    val indicatorOffset by animateDpAsState(
        targetValue = slotStride * currentPage,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "onboardingIndicatorOffset"
    )

    Box(
        modifier = modifier
            .size(width = slotStride * pageCount, height = dotSize),
        contentAlignment = Alignment.CenterStart
    ) {
        repeat(pageCount) { pageIndex ->
            Box(
                modifier = Modifier
                    .offset(x = dotInset + slotStride * pageIndex)
                    .size(width = dotSize, height = dotSize)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = CircleShape
                    )
            )
        }

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .size(width = pillWidth, height = dotSize)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
    }
}

private data class OnboardingPageContent(
    val icon: ImageVector,
    val iconTint: Color,
    val kickerTextRes: Int,
    val titleTextRes: Int,
    val bodyTextRes: Int
)

@Preview(showBackground = true)
@Composable
fun OnboardingDarkPreview() {
    WeatherTheme(true) {
        OnboardingContent(
            page = OnboardingPage.PERMISSION,
            locationGranted = false,
            onAllowAccessClick = {},
            onNextClick = {},
            onSkipClick = {},
            onStartClick = {},
            onPageSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    WeatherTheme(false) {
        OnboardingContent(
            page = OnboardingPage.WELCOME,
            locationGranted = false,
            onAllowAccessClick = {},
            onNextClick = {},
            onSkipClick = {},
            onStartClick = {},
            onPageSelected = {}
        )
    }
}
