package com.pbarthuel.testbfor.ui.models

import com.pbarthuel.testbfor.domain.models.PokemonType

data class PokemonTypeUi(
    val name: String,
    val url: String
)

fun PokemonType.toUi() =
    PokemonTypeUi(
        name = name,
        url = url
    )