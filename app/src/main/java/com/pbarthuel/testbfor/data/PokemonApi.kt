package com.pbarthuel.testbfor.data

import com.pbarthuel.testbfor.data.models.PokemonDetailResponse
import com.pbarthuel.testbfor.data.models.PokemonListByTypeResponse
import com.pbarthuel.testbfor.data.models.PokemonListResponse
import com.pbarthuel.testbfor.data.models.PokemonTypeListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    @GET("type")
    suspend fun getAllPokemonType(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonTypeListResponse

    @GET
    suspend fun getPokemonListByType(
        @Url url: String
    ): PokemonListByTypeResponse

    @GET
    suspend fun getPreviousAndNextPokemonList(
        @Url url: String
    ): PokemonListResponse

    @GET
    suspend fun getPokemonDetail(
        @Url url: String
    ): PokemonDetailResponse
}