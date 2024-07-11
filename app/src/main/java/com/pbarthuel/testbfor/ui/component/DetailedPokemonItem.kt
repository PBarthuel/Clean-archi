package com.pbarthuel.testbfor.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pbarthuel.testbfor.R
import com.pbarthuel.testbfor.ui.models.PokemonUi

@Composable
fun DetailedPokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonUi.DetailedPokemonUi
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(72.dp),
            model = pokemon.imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground)
        )
        Column(modifier = Modifier.align(CenterVertically)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "#${pokemon.id}")
                Text(text = pokemon.name)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = stringResource(R.string.pokemon_height, pokemon.height * 10))
                Text(text = stringResource(R.string.pokemon_weight, pokemon.weight / 10))
            }
        }
    }
}

@Preview
@Composable
private fun DetailedPokemonItemPreview() {
    DetailedPokemonItem(
        modifier = Modifier.background(color = Color.Gray),
        pokemon = PokemonUi.DetailedPokemonUi(
            name = "Bulbasaur",
            id = 1,
            height = 7,
            weight = 69,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        )
    )
}