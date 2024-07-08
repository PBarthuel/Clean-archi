package com.pbarthuel.testbfor.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pbarthuel.testbfor.data.models.Pokemon
import com.pbarthuel.testbfor.data.models.PokemonDetailResponse
import com.pbarthuel.testbfor.data.PokemonListApi
import com.pbarthuel.testbfor.data.models.PokemonListByTypeResponse
import com.pbarthuel.testbfor.data.models.PokemonListResponse
import com.pbarthuel.testbfor.data.models.PokemonSlot
import com.pbarthuel.testbfor.data.models.PokemonType
import com.pbarthuel.testbfor.data.models.PokemonTypeListResponse
import com.pbarthuel.testbfor.data.models.Sprites
import com.pbarthuel.testbfor.ui.models.PokemonTypeUi
import com.pbarthuel.testbfor.ui.models.PokemonUi
import com.pbarthuel.testbfor.ui.models.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var pokemonListApi: PokemonListApi
    private lateinit var viewModel: PokemonListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        pokemonListApi = mockk()

        val pokemonListResponse = PokemonListResponse(
            next = "nextUrl",
            previous = "previousUrl",
            count = 1,
            results = listOf(
                Pokemon("Bulbasaur", "url1"),
                Pokemon("Ivysaur", "url2")
            )
        )
        val pokemonTypeResponse = PokemonTypeListResponse(
            next = "nextUrl",
            previous = "previousUrl",
            count = 1,
            results = listOf(
                PokemonType("Grass", "url1"),
                PokemonType("Poison", "url2")
            )
        )

        coEvery { pokemonListApi.getPokemonList(0, 20) } returns pokemonListResponse
        coEvery { pokemonListApi.getAllPokemonType(0, 40) } returns pokemonTypeResponse

        viewModel = PokemonListViewModel(pokemonListApi, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `init should fetch initial pokemon list and types`() = runTest {
        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = "nextUrl",
            previousUrl = "previousUrl",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Bulbasaur", "url1"),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState, viewModel.uiState.first())
    }

    @Test
    fun `getPokemonsFromType should fetch pokemon list by type`() = runTest {
        advanceUntilIdle()

        val expectedPokemonListResponse = PokemonListByTypeResponse(
            pokemon = listOf(
                PokemonSlot(Pokemon("Bulbasaur", "url1")),
                PokemonSlot(Pokemon("Ivysaur", "url2"))
            )
        )

        coEvery { pokemonListApi.getPokemonListByType("url1") } returns expectedPokemonListResponse

        viewModel.getPokemonsFromType("url1")

        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = null,
            previousUrl = null,
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Bulbasaur", "url1"),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState, viewModel.uiState.first())
    }

    @Test
    fun `getPokemonsFromType should fetch pokemon list by type error`() = runTest {
        advanceUntilIdle()

        coEvery { pokemonListApi.getPokemonListByType("url1") } throws Exception()

        viewModel.getPokemonsFromType("url1")

        advanceUntilIdle()

        assertEquals(UiState.Error, viewModel.uiState.first())
    }

    @Test
    fun `resetPokemonType should fetch initial pokemon list`() = runTest {
        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = "nextUrl",
            previousUrl = "previousUrl",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Bulbasaur", "url1"),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState, viewModel.uiState.first())

        val expectedPokemonListResponse = PokemonListResponse(
            next = "nextUrl",
            previous = "previousUrl",
            count = 1,
            results = listOf(
                Pokemon("Bulbasaur", "url1"),
                Pokemon("Ivysaur", "url2")
            )
        )

        coEvery { pokemonListApi.getPokemonList(0, 20) } returns expectedPokemonListResponse

        viewModel.resetPokemonType()

        advanceUntilIdle()

        assertEquals(expectedState, viewModel.uiState.first())
    }

    @Test
    fun `resetPokemonType should fetch initial pokemon list error`() = runTest {
        advanceUntilIdle()

        coEvery { pokemonListApi.getPokemonList(0, 20) } throws Exception()

        viewModel.resetPokemonType()

        advanceUntilIdle()

        assertEquals(UiState.Error, viewModel.uiState.first())
    }

    @Test
    fun `getPokemonDetail should fetch pokemon detail`() = runTest {
        advanceUntilIdle()

        val expectedPokemonDetail = PokemonDetailResponse(
            name = "Bulbasaur",
            id = 1,
            height = 7,
            weight = 69,
            sprites = Sprites(frontDefault = "frontImageUrl"),
        )

        coEvery { pokemonListApi.getPokemonDetail("url1") } returns expectedPokemonDetail

        viewModel.getPokemonDetail(Pokemon(name = "Bulbasaur", url = "url1"))

        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = "nextUrl",
            previousUrl = "previousUrl",
            pokemonList = listOf(
                PokemonUi.DetailedPokemonUi(
                    name = "Bulbasaur",
                    id = 1,
                    height = 7,
                    weight = 69,
                    frontImageUrl = "frontImageUrl"
                    ),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList(),
        )

        assertEquals(expectedState, viewModel.uiState.first())
    }

    @Test
    fun `getPokemonDetail should fetch pokemon detail error`() = runTest {
        advanceUntilIdle()

        coEvery { pokemonListApi.getPokemonDetail("url1") } throws Exception()

        viewModel.getPokemonDetail(Pokemon(name = "Bulbasaur", url = "url1"))

        advanceUntilIdle()

        assertEquals(UiState.Error, viewModel.uiState.first())
    }

    @Test
    fun `nextPage should fetch next page of pokemon list`() = runTest {
        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = "nextUrl",
            previousUrl = "previousUrl",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Bulbasaur", "url1"),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState, viewModel.uiState.first())

        val expectedPokemonListResponse = PokemonListResponse(
            next = "nextUrl2",
            previous = "url1",
            count = 1,
            results = listOf(
                Pokemon("Venusaur", "url3"),
                Pokemon("Charmander", "url4")
            )
        )

        coEvery { pokemonListApi.getPreviousAndNextPokemonList("nextUrl") } returns expectedPokemonListResponse

        viewModel.nextPage()

        advanceUntilIdle()

        val expectedState2 = UiState.Success(
            nextUrl = "nextUrl2",
            previousUrl = "url1",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Venusaur", "url3"),
                PokemonUi.LightPokemonUi("Charmander", "url4")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState2, viewModel.uiState.first())
    }

    @Test
    fun `nextPage should fetch next page of pokemon list error `() = runTest {
        advanceUntilIdle()

        coEvery { pokemonListApi.getPreviousAndNextPokemonList("nextUrl") } throws Exception()

        viewModel.nextPage()

        advanceUntilIdle()

        assertEquals(UiState.Error, viewModel.uiState.first())
    }

    @Test
    fun `previousPage should fetch previous page of pokemon list`() = runTest {
        advanceUntilIdle()

        val expectedState = UiState.Success(
            nextUrl = "nextUrl",
            previousUrl = "previousUrl",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Bulbasaur", "url1"),
                PokemonUi.LightPokemonUi("Ivysaur", "url2")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState, viewModel.uiState.first())

        val expectedPokemonListResponse = PokemonListResponse(
            next = "url1",
            previous = "previousUrl2",
            count = 1,
            results = listOf(
                Pokemon("Charmander", "url4"),
                Pokemon("Squirtle", "url5")
            )
        )

        coEvery { pokemonListApi.getPreviousAndNextPokemonList("previousUrl") } returns expectedPokemonListResponse

        viewModel.previousPage()

        advanceUntilIdle()

        val expectedState2 = UiState.Success(
            nextUrl = "url1",
            previousUrl = "previousUrl2",
            pokemonList = listOf(
                PokemonUi.LightPokemonUi("Charmander", "url4"),
                PokemonUi.LightPokemonUi("Squirtle", "url5")
            ).toImmutableList(),
            pokemonTypeList = listOf(
                PokemonTypeUi("Grass", "url1"),
                PokemonTypeUi("Poison", "url2")
            ).toImmutableList()
        )

        assertEquals(expectedState2, viewModel.uiState.first())
    }

    @Test
    fun `previousPage should fetch previous page of pokemon list error`() = runTest {
        advanceUntilIdle()

        coEvery { pokemonListApi.getPreviousAndNextPokemonList("previousUrl") } throws Exception()

        viewModel.previousPage()

        advanceUntilIdle()

        assertEquals(UiState.Error, viewModel.uiState.first())
    }
}