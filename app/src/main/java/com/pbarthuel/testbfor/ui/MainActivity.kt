package com.pbarthuel.testbfor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pbarthuel.testbfor.R
import com.pbarthuel.testbfor.ui.component.DropdownTextField
import com.pbarthuel.testbfor.ui.component.PokemonList
import com.pbarthuel.testbfor.ui.models.UiState
import com.pbarthuel.testbfor.ui.theme.TestBforTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PokemonListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestBforTheme {
                val uiState by viewModel.uiState.collectAsState()
                when (val state = uiState) {
                    UiState.Error -> Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.align(CenterHorizontally),
                            text = stringResource(R.string.generic_error)
                        )
                    }

                    UiState.Loading -> Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(CenterHorizontally)
                        )
                    }

                    is UiState.Success -> Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            DropdownTextField(
                                pokemonListType = state.pokemonTypeList,
                                onTypeSelected = { url -> viewModel.getPokemonsFromType(url) },
                                onResetType = { viewModel.resetPokemonType() }
                            )
                        }) { innerPadding ->
                        PokemonList(
                            modifier = Modifier.padding(innerPadding),
                            uiState = state,
                            onPreviousButtonClicked = { viewModel.previousPage() },
                            onNextButtonClicked = { viewModel.nextPage() },
                            onGetPokemonDetailsClicked = { pokemon ->
                                viewModel.getPokemonDetail(pokemon)
                            }
                        )
                    }
                }
            }
        }
    }
}