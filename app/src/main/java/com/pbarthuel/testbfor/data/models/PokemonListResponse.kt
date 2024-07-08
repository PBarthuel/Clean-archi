package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("results") val results: List<Pokemon>
)

@Serializable
data class Pokemon(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
