package com.pbarthuel.testbfor.ui.models

import com.pbarthuel.testbfor.domain.models.PokemonType
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonTypeUiTest {

    @Test
    fun `test toUi`() {
        // Given
        val pokemonType = PokemonType(
            name = "name",
            url = "url"
        )

        // When
        val result = pokemonType.toUi()

        // Then
        assertEquals("name", result.name)
        assertEquals("url", result.url)
    }
}