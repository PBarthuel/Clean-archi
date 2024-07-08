package com.pbarthuel.testbfor.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.pbarthuel.testbfor.R
import com.pbarthuel.testbfor.ui.models.PokemonTypeUi

@Composable
fun DropdownTextField(
    modifier: Modifier = Modifier,
    pokemonListType: List<PokemonTypeUi>,
    onTypeSelected: (String) -> Unit,
    onResetType: () -> Unit
) {
    var text by remember { mutableStateOf("Search pokemons with type !") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            onClick = { expanded = !expanded }
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.generic_reset),
                        modifier = Modifier.align(CenterHorizontally),
                        color = Color.Red
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { expanded = false },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_clear_24),
                            contentDescription = "Back"
                        )
                    }
                },
                onClick = {
                    text = "Search pokemons with type !"
                    expanded = false
                    onResetType()
                }
            )
            pokemonListType.forEachIndexed { index, pokemonType ->
                DropdownMenuItem(
                    text = { Text(text = pokemonType.name) },
                    onClick = {
                        text = pokemonType.name
                        expanded = false
                        onTypeSelected(pokemonType.url)
                    }
                )
                if (index != pokemonListType.size - 1) {
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
}

@Preview
@Composable
private fun DropdownTextFieldPreview() {
    DropdownTextField(
        modifier = Modifier.background(color = Color.Gray),
        pokemonListType = listOf(
            PokemonTypeUi("https://pokeapi.co/api/v2/type/1/", "normal"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/2/", "fighting"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/3/", "flying"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/4/", "poison"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/5/", "ground"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/6/", "rock"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/7/", "bug"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/8/", "ghost"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/9/", "steel"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/10/", "fire"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/11/", "water"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/12/", "grass"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/13/", "electric"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/14/", "psychic"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/15/", "ice"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/16/", "dragon"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/17/", "dark"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/18/", "fairy"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/10001/", "unknown"),
            PokemonTypeUi("https://pokeapi.co/api/v2/type/10002/", "shadow")
        ),
        onTypeSelected = {},
        onResetType = {}
    )
}