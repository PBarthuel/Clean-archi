package com.pbarthuel.testbfor.data

import com.pbarthuel.testbfor.domain.PokemonRepository
import com.pbarthuel.testbfor.domain.models.Pokemon
import com.pbarthuel.testbfor.domain.models.PokemonDetail
import com.pbarthuel.testbfor.domain.models.PokemonPage
import com.pbarthuel.testbfor.domain.models.PokemonType
import com.pbarthuel.testbfor.domain.models.toDomain
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
): PokemonRepository {

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonPage =
        pokemonApi.getPokemonList(offset, limit).toDomain()

    override suspend fun getAllPokemonType(offset: Int, limit: Int): List<PokemonType> =
        pokemonApi.getAllPokemonType(offset, limit).results.map { it.toDomain() }

    override suspend fun getPokemonListByType(url: String): List<Pokemon> =
        pokemonApi.getPokemonListByType(url).pokemon.map { it.pokemonWs.toDomain() }

    override suspend fun getPreviousAndNextPokemonList(url: String): PokemonPage =
        pokemonApi.getPreviousAndNextPokemonList(url).toDomain()

    override suspend fun getPokemonDetail(url: String): PokemonDetail =
        pokemonApi.getPokemonDetail(url).toDomain()
}