package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListByTypeResponse(
    @SerialName("pokemon") val pokemon: List<PokemonSlot>,
)

@Serializable
data class PokemonSlot(
    @SerialName("pokemon") val pokemon: Pokemon
)