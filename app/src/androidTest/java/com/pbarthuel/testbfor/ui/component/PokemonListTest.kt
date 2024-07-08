package com.pbarthuel.testbfor.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pbarthuel.testbfor.ui.models.PokemonUi
import com.pbarthuel.testbfor.ui.models.UiState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonListDisplaysCorrectly() {
        val samplePokemons = persistentListOf(
            PokemonUi.LightPokemonUi(name = "Bulbasaur", url = "url1"),
            PokemonUi.LightPokemonUi(name = "Charmander", url = "url2"),
            PokemonUi.LightPokemonUi(name = "Squirtle", url = "url3")
        )

        val uiState = UiState.Success(
            previousUrl = "previous_url",
            nextUrl = "next_url",
            pokemonList = samplePokemons,
        )

        composeTestRule.setContent {
            PokemonList(
                uiState = uiState,
                onPreviousButtonClicked = {},
                onNextButtonClicked = {},
                onGetPokemonDetailsClicked = {}
            )
        }

        // Verify that each Pokemon name is displayed
        samplePokemons.forEach { pokemon ->
            composeTestRule.onNodeWithText(pokemon.name).assertIsDisplayed()
        }

        // Verify that navigation buttons are displayed and clickable
        composeTestRule.onNodeWithText("Previous").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
        composeTestRule.onNodeWithText("Previous").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
    }

    @Test
    fun detailedPokemonItemDisplaysCorrectly() {
        val samplePokemons = persistentListOf(
            PokemonUi.DetailedPokemonUi(
                id = 1,
                name = "Bulbasaur",
                frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                height = 7,
                weight = 60
            ),
            PokemonUi.LightPokemonUi(name = "Charmander", url = "url2"),
        )

        val uiState = UiState.Success(
            previousUrl = null,
            nextUrl = null,
            pokemonList = samplePokemons
        )

        composeTestRule.setContent {
            PokemonList(
                uiState = uiState,
                onPreviousButtonClicked = {},
                onNextButtonClicked = {},
                onGetPokemonDetailsClicked = {}
            )
        }

        // Verify that the Pokemon name is displayed and button work
        composeTestRule.onNodeWithText("Charmander").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Load Details").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Load Details").performClick()

        // Verify that the detailed Pokemon information is displayed
        composeTestRule.onNodeWithText("#1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("Height: 70cm").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight: 6kg").assertIsDisplayed()

        // Verify that navigation buttons are displayed and disabled
        composeTestRule.onNodeWithText("Previous").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
        composeTestRule.onNodeWithText("Previous").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Next").assertIsNotEnabled()
    }
}