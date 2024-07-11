package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonListResponse

data class PokemonPage(
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

fun PokemonListResponse.toDomain() =
    PokemonPage(
        next = next,
        previous = previous,
        results = results.map { it.toDomain() }
    )