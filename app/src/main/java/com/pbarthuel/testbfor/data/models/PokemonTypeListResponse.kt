package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeListResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("results") val results: List<PokemonType>
)

@Serializable
data class PokemonType(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)