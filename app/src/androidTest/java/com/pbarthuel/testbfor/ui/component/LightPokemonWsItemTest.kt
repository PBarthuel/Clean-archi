package com.pbarthuel.testbfor.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pbarthuel.testbfor.ui.models.PokemonUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LightPokemonWsItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lightPokemonItemDisplaysCorrectly() {
        val pokemon = PokemonUi.LightPokemonUi(
            name = "Bulbasaur",
            imageUrl = "https://pokeapi.co/api/v2/pokemon/1/"
        )

        composeTestRule.setContent {
            LightPokemonItem(
                pokemon = pokemon,
                onLoadDetailClicked = {}
            )
        }

        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Load Details").assertIsDisplayed()
    }

    @Test
    fun loadDetailsButtonClick() {
        val pokemon = PokemonUi.LightPokemonUi(
            name = "Bulbasaur",
            imageUrl = "https://pokeapi.co/api/v2/pokemon/1/"
        )
        var clickedUrl: String? = null

        composeTestRule.setContent {
            LightPokemonItem(
                pokemon = pokemon,
                onLoadDetailClicked = { url -> clickedUrl = url }
            )
        }

        composeTestRule.onNodeWithContentDescription("Load Details").performClick()
        assert(clickedUrl == pokemon.imageUrl)
    }
}
