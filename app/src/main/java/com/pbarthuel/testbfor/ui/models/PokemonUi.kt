package com.pbarthuel.testbfor.ui.models

import com.pbarthuel.testbfor.domain.models.Pokemon
import com.pbarthuel.testbfor.domain.models.PokemonDetail

sealed class PokemonUi(
    open val name: String
) {
    data class LightPokemonUi(
        override val name: String,
        val imageUrl: String
    ) : PokemonUi(name = name)

    data class DetailedPokemonUi(
        override val name: String,
        val id: Int,
        val height: Int,
        val weight: Int,
        val imageUrl: String
    ) : PokemonUi(name = name)
}

fun Pokemon.toUi() =
    PokemonUi.LightPokemonUi(
        name = name,
        imageUrl = detailUrl
    )

fun PokemonDetail.toUi() =
    PokemonUi.DetailedPokemonUi(
        name = name,
        id = id,
        height = height,
        weight = weight,
        imageUrl = imageUrl
    )