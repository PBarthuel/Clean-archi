package com.pbarthuel.testbfor.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pbarthuel.testbfor.ui.models.PokemonUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailedPokemonItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailedPokemonItemDisplaysCorrectly() {
        val pokemon = PokemonUi.DetailedPokemonUi(
            id = 1,
            name = "Bulbasaur",
            frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            height = 7,
            weight = 60
        )

        composeTestRule.setContent {
            DetailedPokemonItem(pokemon = pokemon)
        }

        composeTestRule.onNodeWithText("#1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("Height: 70cm").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight: 6kg").assertIsDisplayed()
    }
}