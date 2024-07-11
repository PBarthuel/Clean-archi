package com.pbarthuel.testbfor.domain.models

import com.pbarthuel.testbfor.data.models.PokemonDetailResponse

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
)

fun PokemonDetailResponse.toDomain() =
    PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = spritesWs.frontDefault ?: ""
    )