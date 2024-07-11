package com.pbarthuel.testbfor.domain.di

import com.pbarthuel.testbfor.domain.PokemonRepository
import com.pbarthuel.testbfor.data.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepository: PokemonRepositoryImpl
    ): PokemonRepository
}