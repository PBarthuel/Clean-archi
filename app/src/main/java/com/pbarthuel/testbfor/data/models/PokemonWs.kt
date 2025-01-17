package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PokemonWs(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)