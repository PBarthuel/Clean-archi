package com.pbarthuel.testbfor.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbarthuel.testbfor.R
import com.pbarthuel.testbfor.ui.models.PokemonUi

@Composable
fun LightPokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonUi.LightPokemonUi,
    onLoadDetailClicked: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = pokemon.name,
            modifier = Modifier.align(CenterVertically)
        )
        IconButton(
            onClick = { onLoadDetailClicked(pokemon.url) }
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_cached_24),
                contentDescription = stringResource(R.string.load_details)
            )
        }
    }
}

@Preview
@Composable
private fun LightPokemonItemPreview() {
    LightPokemonItem(
        modifier = Modifier.background(color = Color.Gray),
        pokemon = PokemonUi.LightPokemonUi(
            name = "Bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        ),
        onLoadDetailClicked = {}
    )
}