package com.pbarthuel.testbfor.ui.models

import com.pbarthuel.testbfor.domain.models.Pokemon
import com.pbarthuel.testbfor.domain.models.PokemonDetail
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonUiTest {

    @Test
    fun `test toUi`() {
        // Given
        val pokemon = Pokemon(
            name = "name",
            detailUrl = "url"
        )

        // When
        val result = pokemon.toUi()

        // Then
        assertEquals("name", result.name)
        assertEquals("url", result.imageUrl)
    }

    @Test
    fun `test detail toUi`() {
        // Given
        val pokemonDetail = PokemonDetail(
            name = "name",
            id = 1,
            height = 1,
            weight = 1,
            imageUrl = "url"
        )

        // When
        val result = pokemonDetail.toUi()

        // Then
        assertEquals("name", result.name)
        assertEquals(1, result.id)
        assertEquals(1, result.height)
        assertEquals(1, result.weight)
        assertEquals("url", result.imageUrl)
    }
}