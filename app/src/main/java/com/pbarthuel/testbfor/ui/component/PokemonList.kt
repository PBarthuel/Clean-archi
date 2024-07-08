package com.pbarthuel.testbfor.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbarthuel.testbfor.R
import com.pbarthuel.testbfor.data.models.Pokemon
import com.pbarthuel.testbfor.ui.models.PokemonUi
import com.pbarthuel.testbfor.ui.models.UiState
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    uiState: UiState.Success,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onGetPokemonDetailsClicked: (Pokemon) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = onPreviousButtonClicked,
                    enabled = !uiState.previousUrl.isNullOrEmpty()
                ) { Text(stringResource(R.string.previous)) }
                TextButton(
                    onClick = onNextButtonClicked,
                    enabled = !uiState.nextUrl.isNullOrEmpty()
                ) { Text(stringResource(R.string.next)) }
            }
        }
        itemsIndexed(
            items = uiState.pokemonList,
            key = { _, pokemon -> pokemon.name }
        ) { index, pokemon ->
            val onLoadDetailClicked: (String) -> Unit =
                remember {
                    { url: String ->
                        onGetPokemonDetailsClicked(
                            Pokemon(
                                name = pokemon.name,
                                url = url
                            )
                        )
                    }
                }

            AnimatedContent(
                targetState = pokemon,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = ""
            ) { targetPokemon ->
                when (targetPokemon) {
                    is PokemonUi.LightPokemonUi -> {
                        LightPokemonItem(
                            pokemon = targetPokemon,
                            onLoadDetailClicked = onLoadDetailClicked
                        )
                    }

                    is PokemonUi.DetailedPokemonUi -> {
                        DetailedPokemonItem(pokemon = targetPokemon)
                    }
                }
            }
            if (index != uiState.pokemonList.size - 1) {
                Spacer(
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListPreview() {
    PokemonList(
        modifier = Modifier.background(color = Color.Gray),
        uiState = UiState.Success(
            pokemonList = persistentListOf(
                PokemonUi.LightPokemonUi(
                    name = "Bulbasaur",
                    url = "https://pokeapi.co/api/v2/pokemon/1/"
                ),
                PokemonUi.LightPokemonUi(
                    name = "Ivysaur",
                    url = "https://pokeapi.co/api/v2/pokemon/2/"
                ),
                PokemonUi.DetailedPokemonUi(
                    name = "Venusaur",
                    id = 3,
                    height = 20,
                    weight = 100,
                    frontImageUrl = "https://pokeapi.co/api/v2/pokemon/3/"
                )
            ),
            previousUrl = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20",
            nextUrl = "https://pokeapi.co/api/v2/pokemon?offset=40&limit=20"
        ),
        onPreviousButtonClicked = {},
        onNextButtonClicked = {},
        onGetPokemonDetailsClicked = {}
    )
}