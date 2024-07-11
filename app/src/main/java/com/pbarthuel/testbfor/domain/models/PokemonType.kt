package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonTypeWs

data class PokemonType(
    val name: String,
    val url: String
)

fun PokemonTypeWs.toDomain() =
    PokemonType(
        name = name,
        url = url
    )