package com.android.pocketalchemy

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import com.android.pocketalchemy.firebase.AuthRepository
import com.android.pocketalchemy.ui.theme.PocketAlchemyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 *
 */
@HiltAndroidTest
class PaInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var authRepository: AuthRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    /**
     * Tests functionality of NewRecipeFAB
     * TODO: TEST CLICK SETS SAVED STATE HANDLE
     */
    @Test
    fun createRecipeFABTest() {
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        composeTestRule.activity.setContent {
            PocketAlchemyTheme {
                PaNavHost(authRepository)
            }
        }
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.STARTED)
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("New Recipe", useUnmergedTree = true)
            .assertExists()
            .performClick()

        composeTestRule.waitForIdle()
        // Tests navigation to edit recipe screen
        composeTestRule.onNodeWithText("Edit Recipe").assertExists()
    }
}