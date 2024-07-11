package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonTypeWs
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonTypeTest {

    @Test
    fun `test toDomain`() {
        // Given
        val pokemonTypeWs = PokemonTypeWs(
            name = "name",
            url = "url"
        )

        // When
        val result = pokemonTypeWs.toDomain()

        // Then
        assertEquals("name", result.name)
        assertEquals("url", result.url)
    }
}