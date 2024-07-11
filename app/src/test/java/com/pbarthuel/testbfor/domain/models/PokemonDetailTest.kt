package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonDetailResponse
import com.pbarthuel.testbfor.data.models.SpritesWs
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonDetailTest {

    @Test
    fun `test toDomain`() {
        // Given
        val pokemonDetailWs = PokemonDetailResponse(
            id = 1,
            name = "name",
            height = 1,
            weight = 1,
            spritesWs = SpritesWs(
                frontDefault = "frontDefault"
            )
        )

        // When
        val result = pokemonDetailWs.toDomain()

        // Then
        assertEquals("name", result.name)
        assertEquals(1, result.height)
        assertEquals(1, result.weight)
        assertEquals("frontDefault", result.imageUrl)
        assertEquals(1, result.id)
    }
}