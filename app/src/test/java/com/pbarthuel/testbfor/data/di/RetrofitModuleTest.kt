package com.pbarthuel.testbfor.data.di

import com.pbarthuel.testbfor.data.PokemonApi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RetrofitModuleTest {

    lateinit var retrofitModule: RetrofitModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        retrofitModule = RetrofitModule
    }

    @Test
    fun `test provideRetrofit`() {
        val retrofit = retrofitModule.provideRetrofit()
        assertNotNull(retrofit)
        val baseUrl = retrofit.baseUrl().toString()
        assert(baseUrl == "https://pokeapi.co/api/v2/")
    }

    @Test
    fun `test providePokemonListApi`() {
        val retrofit = mockk<Retrofit>()
        val pokemonApi = mockk<PokemonApi>()

        every { retrofit.create(PokemonApi::class.java) } returns pokemonApi

        val api = retrofitModule.providePokemonListApi(retrofit)
        assertNotNull(api)
    }
}