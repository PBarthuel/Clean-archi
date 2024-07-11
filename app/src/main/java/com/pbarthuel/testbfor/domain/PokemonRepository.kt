package com.pbarthuel.testbfor.domain

import com.pbarthuel.testbfor.domain.models.Pokemon
import com.pbarthuel.testbfor.domain.models.PokemonDetail
import com.pbarthuel.testbfor.domain.models.PokemonPage
import com.pbarthuel.testbfor.domain.models.PokemonType

interface PokemonRepository {

    suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): PokemonPage

    suspend fun getAllPokemonType(
        offset: Int,
        limit: Int
    ): List<PokemonType>

    suspend fun getPokemonListByType(
        url: String
    ): List<Pokemon>

    suspend fun getPreviousAndNextPokemonList(
        url: String
    ): PokemonPage

    suspend fun getPokemonDetail(
        url: String
    ): PokemonDetail
}