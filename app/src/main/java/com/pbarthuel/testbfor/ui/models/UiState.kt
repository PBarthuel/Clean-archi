package com.pbarthuel.testbfor.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class UiState {
    data class Success(
        val nextUrl: String?,
        val previousUrl: String?,
        val pokemonList: ImmutableList<PokemonUi> = persistentListOf(),
        val pokemonTypeList: ImmutableList<PokemonTypeUi> = persistentListOf()
    ) : UiState()

    data object Loading : UiState()
    data object Error : UiState()
}