package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSlotWs(
    @SerialName("pokemon") val pokemonWs: PokemonWs
)