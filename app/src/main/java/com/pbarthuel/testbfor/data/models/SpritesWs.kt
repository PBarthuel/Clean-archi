package com.pbarthuel.testbfor.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpritesWs(
    @SerialName("front_default") val frontDefault: String?
)