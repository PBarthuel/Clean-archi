package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("height") val height: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("sprites") val sprites: Sprites,
)

@Serializable
data class Sprites(
    @SerialName("front_default") val frontDefault: String?
)