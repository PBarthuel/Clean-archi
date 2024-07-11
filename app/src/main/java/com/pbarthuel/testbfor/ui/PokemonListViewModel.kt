package com.pbarthuel.testbfor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbarthuel.testbfor.domain.PokemonRepository
import com.pbarthuel.testbfor.data.di.IoDispatcher
import com.pbarthuel.testbfor.data.models.PokemonWs
import com.pbarthuel.testbfor.ui.models.UiState
import com.pbarthuel.testbfor.ui.models.toUi
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
    private val pokemonRepository: PokemonRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                val pokemonResponse = pokemonRepository.getPokemonList(0, 20)
                val pokemonTypeList = pokemonRepository.getAllPokemonType(0, 40)
                pokemonResponse to pokemonTypeList
            }.onSuccess { (pokemonResponse, pokemonTypeList) ->
                _uiState.update {
                    UiState.Success(
                        nextUrl = pokemonResponse.next,
                        previousUrl = pokemonResponse.previous,
                        pokemonList = pokemonResponse.results.map {
                            it.toUi()
                        }.toImmutableList(),
                        pokemonTypeList = pokemonTypeList.map {
                            it.toUi()
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
                pokemonRepository.getPokemonListByType(url)
            }.onSuccess { pokemonList ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            nextUrl = null,
                            previousUrl = null,
                            pokemonList = pokemonList.map {
                                it.toUi()
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
                pokemonRepository.getPokemonList(0, 20)
            }.onSuccess { pokemonList ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            nextUrl = pokemonList.next,
                            previousUrl = pokemonList.previous,
                            pokemonList = pokemonList.results.map {
                                it.toUi()
                            }.toImmutableList()
                        )
                    } else {
                        UiState.Error
                    }
                }
            }.onFailure { _uiState.update { UiState.Error } }
        }
    }

    fun getPokemonDetail(pokemonWs: PokemonWs) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                pokemonRepository.getPokemonDetail(pokemonWs.url)
            }.onSuccess { pokemonDetail ->
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        state.copy(
                            pokemonList = state.pokemonList.map { findPokemon ->
                                if (findPokemon.name == pokemonWs.name) {
                                    pokemonDetail.toUi()
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
                        val pokemonResponse = state.nextUrl?.let { pokemonRepository.getPreviousAndNextPokemonList(it) }
                            ?: throw Exception("Next URL is null")

                        state.copy(
                            nextUrl = pokemonResponse.next,
                            previousUrl = pokemonResponse.previous,
                            pokemonList = pokemonResponse.results.map {
                                it.toUi()
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
                        val pokemonResponse = state.previousUrl?.let { pokemonRepository.getPreviousAndNextPokemonList(it) }
                            ?: throw Exception("Next URL is null")

                        state.copy(
                            nextUrl = pokemonResponse.next,
                            previousUrl = pokemonResponse.previous,
                            pokemonList = pokemonResponse.results.map {
                                it.toUi()
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



