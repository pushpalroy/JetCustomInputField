package com.appmason.jetcustominputfield

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import com.appmason.jetcustominputfield.ui.components.SSNInputField
import org.junit.Rule
import org.junit.Test

class SSNInputFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ssnInputField_InputHandling() {
        composeTestRule.setContent {
            val ssnState = remember { mutableStateOf(TextFieldValue()) }
            SSNInputField(
                modifier = Modifier,
                ssn = ssnState
            )
        }
        // Simulate user input
        val input = "123456789"

        // Perform text input on the node with the specified testTag
        composeTestRule.onNodeWithTag("ssn_input_field")
            .assertExists()
            .performTextInput(input)

        // Assert the formatted text is displayed
        composeTestRule.onNodeWithTag("ssn_input_field")
            .assertTextEquals("123 - 45 - 6789")
    }

    @Test
    fun ssnInputField_VisibilityToggle() {
        // Set up the test environment with an initial SSN value
        composeTestRule.setContent {
            val ssnState = remember { mutableStateOf(TextFieldValue("123456789")) }
            SSNInputField(
                modifier = Modifier,
                ssn = ssnState
            )
        }

        // Initial assertion: SSN is in the default visibility state
        // Assuming the default state is unmasked
        composeTestRule.onNodeWithTag("ssn_input_field")
            .assertTextEquals("123 - 45 - 6789")

        // Find and click the toggle icon
        // You need to ensure your IconToggleButton has a distinct testTag or contentDescription
        composeTestRule.onNodeWithContentDescription("Toggle SSN visibility")
            .performClick()

        // Assert the SSN is now masked
        // The text should now be masked, assuming the masked format is "••• - •• - ••••"
        composeTestRule.onNodeWithTag("ssn_input_field")
            .assertTextEquals("••• - •• - ••••")

        // Click again to show the SSN
        composeTestRule.onNodeWithContentDescription("Toggle SSN visibility")
            .performClick()

        // Assert the SSN is visible again
        composeTestRule.onNodeWithTag("ssn_input_field")
            .assertTextEquals("123 - 45 - 6789")
    }
}
