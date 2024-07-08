package com.pbarthuel.testbfor.ui.models

sealed class PokemonUi(
    open val name: String
) {
    data class LightPokemonUi(
        override val name: String,
        val url: String
    ) : PokemonUi(name = name)

    data class DetailedPokemonUi(
        override val name: String,
        val id: Int,
        val height: Int,
        val weight: Int,
        val frontImageUrl: String
    ) : PokemonUi(name = name)
}