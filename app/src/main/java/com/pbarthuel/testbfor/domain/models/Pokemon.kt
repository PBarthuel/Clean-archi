package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonWs

data class Pokemon(
    val name: String,
    val detailUrl: String
)

fun PokemonWs.toDomain() =
    Pokemon(
        name = name,
        detailUrl = url
    )