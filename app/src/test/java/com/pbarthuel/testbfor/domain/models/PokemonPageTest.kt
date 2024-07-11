package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonListResponse
import com.pbarthuel.testbfor.data.models.PokemonTypeListResponse
import com.pbarthuel.testbfor.data.models.PokemonWs
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonPageTest {

    @Test
    fun `test toDomain`() {
        // Given
        val pokemonPageWs = PokemonListResponse(
            count = 1,
            next = "next",
            previous = "previous",
            results = listOf(
                PokemonWs(
                    name = "name",
                    url = "url"
                )
            )
        )

        // When
        val result = pokemonPageWs.toDomain()

        // Then
        assertEquals("next", result.next)
        assertEquals("previous", result.previous)
        assertEquals(1, result.results.size)
        assertEquals("name", result.results[0].name)
        assertEquals("url", result.results[0].detailUrl)
    }
}