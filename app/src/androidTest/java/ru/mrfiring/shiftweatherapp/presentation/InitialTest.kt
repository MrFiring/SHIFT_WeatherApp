package ru.mrfiring.shiftweatherapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InitialTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appRuns() {
        composeTestRule.onNodeWithText("Weather").assertExists()
    }

    @Test
    fun initialLoadingInProgress() {
        composeTestRule.onNodeWithText("Loading...").assertExists()

    }

    @Test
    fun openDrawerAndCheck() {
        composeTestRule.onNodeWithTag("DrawerButton").performClick()

    }

    @Test
    fun openDrawerAndGoToFavorites() {
        composeTestRule.onNodeWithTag("DrawerButton").performClick()

        composeTestRule.onNodeWithText("Favorites").performClick()

        composeTestRule.onNodeWithTag("FavoriteLazy").assertIsDisplayed()

    }

}
