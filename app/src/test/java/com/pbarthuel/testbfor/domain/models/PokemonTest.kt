package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonWs
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonTest {

    @Test
    fun `test toDomain`() {
        // Given
        val pokemonWs = PokemonWs(
            name = "name",
            url = "url"
        )

        // When
        val result = pokemonWs.toDomain()

        // Then
        assertEquals("name", result.name)
        assertEquals("url", result.detailUrl)
    }
}