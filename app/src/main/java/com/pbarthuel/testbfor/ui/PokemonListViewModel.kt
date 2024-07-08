package com.pbarthuel.testbfor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbarthuel.testbfor.data.PokemonListApi
import com.pbarthuel.testbfor.data.di.IoDispatcher
import com.pbarthuel.testbfor.data.models.Pokemon
import com.pbarthuel.testbfor.ui.models.PokemonTypeUi
import com.pbarthuel.testbfor.ui.models.PokemonUi
import com.pbarthuel.testbfor.ui.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListApi: PokemonListApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                val pokemonResponse = pokemonListApi.getPokemonList(0, 20)
                val pokemonTypeList = pokemonListApi.getAllPokemonType(0, 40)
                pokemonResponse to pokemonTypeList
            }.onSuccess { (pokemonResponse, pokemonTypeList) ->
                _uiState.update {
                    UiState.Success(
                        nextUrl = pokemonResponse.next,
                        previousUrl = pokemonResponse.previous,
                        pokemonList = pokemonResponse.results.map {
                            PokemonUi.LightPokemonUi(it.name, it.url)
                        }.toImmutableList(),
                        pokemonTypeList = pokemonTypeList.results.map {
                            PokemonTypeUi(it.name, it.url)
                        }.toImmutableList()
                    )
                }
            }.onFailure {
                _uiState.update { UiState.Error }
            }
        }
    }

    fun getPokemonsFromType(url: String) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                pokemonListApi.getPokemonListByType(url)
            }.onSuccess { pokemonList ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            nextUrl = null,
                            previousUrl = null,
                            pokemonList = pokemonList.pokemon.map {
                                PokemonUi.LightPokemonUi(it.pokemon.name, it.pokemon.url)
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure { _uiState.update { UiState.Error } }
        }
    }

    fun resetPokemonType() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                pokemonListApi.getPokemonList(0, 20)
            }.onSuccess { pokemonList ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            nextUrl = pokemonList.next,
                            previousUrl = pokemonList.previous,
                            pokemonList = pokemonList.results.map {
                                PokemonUi.LightPokemonUi(it.name, it.url)
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure { _uiState.update { UiState.Error } }
        }
    }

    fun getPokemonDetail(pokemon: Pokemon) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                pokemonListApi.getPokemonDetail(pokemon.url)
            }.onSuccess { pokemonDetail ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            pokemonList = state.pokemonList.map { findPokemon ->
                                if (findPokemon.name == pokemon.name) {
                                    PokemonUi.DetailedPokemonUi(
                                        id = pokemonDetail.id,
                                        name = findPokemon.name,
                                        height = pokemonDetail.height,
                                        weight = pokemonDetail.weight,
                                        frontImageUrl = pokemonDetail.sprites.frontDefault ?: ""
                                    )
                                } else {
                                    findPokemon
                                }
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure { _uiState.update { UiState.Error } }
        }
    }

    fun nextPage() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        val pokemonResponse = state.nextUrl?.let { pokemonListApi.getPreviousAndNextPokemonList(it) }
                            ?: throw Exception("Next URL is null")

                        state.copy(
                            nextUrl = pokemonResponse.next,
                            previousUrl = pokemonResponse.previous,
                            pokemonList = pokemonResponse.results.map {
                                PokemonUi.LightPokemonUi(it.name, it.url)
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure {
                _uiState.update { UiState.Error }
            }
        }
    }

    fun previousPage() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        val pokemonResponse = state.previousUrl?.let { pokemonListApi.getPreviousAndNextPokemonList(it) }
                            ?: throw Exception("Next URL is null")

                        state.copy(
                            nextUrl = pokemonResponse.next,
                            previousUrl = pokemonResponse.previous,
                            pokemonList = pokemonResponse.results.map {
                                PokemonUi.LightPokemonUi(it.name, it.url)
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure {
                _uiState.update { UiState.Error }
            }
        }
    }
}



