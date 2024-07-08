package com.pbarthuel.testbfor.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pbarthuel.testbfor.ui.models.PokemonTypeUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DropdownTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDropdownTextField() {
        val pokemonTypes = listOf(
            PokemonTypeUi(name = "Fire", url = "url1"),
            PokemonTypeUi(name = "Water", url = "url2")
        )

        var selectedType = ""
        var resetCalled = false

        composeTestRule.setContent {
            DropdownTextField(
                pokemonListType = pokemonTypes,
                onTypeSelected = { selectedType = it },
                onResetType = { resetCalled = true }
            )
        }

        // Check initial state
        composeTestRule.onNodeWithText("Search pokemons with type !").assertIsDisplayed()

        // Open the dropdown
        composeTestRule.onNodeWithText("Search pokemons with type !").performClick()
        composeTestRule.onNodeWithText("Reset").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fire").assertIsDisplayed()
        composeTestRule.onNodeWithText("Water").assertIsDisplayed()

        // Select a type
        composeTestRule.onNodeWithText("Fire").performClick()
        composeTestRule.onNodeWithText("Fire").assertIsDisplayed()
        assert(selectedType == "url1")

        // Open the dropdown again and reset
        composeTestRule.onNodeWithText("Fire").performClick()
        composeTestRule.onNodeWithText("Reset").performClick()
        assert(resetCalled)

        // Open the dropdown and back
        composeTestRule.onNodeWithText("Search pokemons with type !").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search pokemons with type !").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Search pokemons with type !").assertIsDisplayed()
    }
}