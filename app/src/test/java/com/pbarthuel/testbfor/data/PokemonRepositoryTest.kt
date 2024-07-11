import com.pbarthuel.testbfor.data.PokemonApi
import com.pbarthuel.testbfor.domain.PokemonRepository
import com.pbarthuel.testbfor.data.models.PokemonDetailResponse
import com.pbarthuel.testbfor.data.models.PokemonListByTypeResponse
import com.pbarthuel.testbfor.data.models.PokemonListResponse
import com.pbarthuel.testbfor.data.models.PokemonTypeListResponse
import com.pbarthuel.testbfor.data.models.SpritesWs
import com.pbarthuel.testbfor.data.PokemonRepositoryImpl
import com.pbarthuel.testbfor.domain.models.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryImplTest {

    @MockK
    lateinit var pokemonApi: PokemonApi

    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pokemonRepository = PokemonRepositoryImpl(pokemonApi)
    }

    @Test
    fun `test getPokemonList`() = runBlockingTest {
        // Given
        val offset = 0
        val limit = 10
        val apiResponse = PokemonListResponse(0, "", "", listOf())
        coEvery { pokemonApi.getPokemonList(offset, limit) } returns apiResponse

        // When
        val result = pokemonRepository.getPokemonList(offset, limit)

        // Then
        assertEquals(apiResponse.toDomain(), result)
        coVerify { pokemonApi.getPokemonList(offset, limit) }
    }

    @Test
    fun `test getAllPokemonType`() = runBlockingTest {
        // Given
        val offset = 0
        val limit = 10
        val apiResponse = PokemonTypeListResponse(
            count = 0, previous = "", next = "", results = listOf()
        )
        coEvery { pokemonApi.getAllPokemonType(offset, limit) } returns apiResponse

        // When
        val result = pokemonRepository.getAllPokemonType(offset, limit)

        // Then
        assertEquals(apiResponse.results.map { it.toDomain() }, result)
        coVerify { pokemonApi.getAllPokemonType(offset, limit) }
    }

    @Test
    fun `test getPokemonListByType`() = runBlockingTest {
        // Given
        val url = "test_url"
        val apiResponse = PokemonListByTypeResponse(
            pokemon = listOf()
        )
        coEvery { pokemonApi.getPokemonListByType(url) } returns apiResponse

        // When
        val result = pokemonRepository.getPokemonListByType(url)

        // Then
        assertEquals(apiResponse.pokemon.map { it.pokemonWs.toDomain() }, result)
        coVerify { pokemonApi.getPokemonListByType(url) }
    }

    @Test
    fun `test getPreviousAndNextPokemonList`() = runBlockingTest {
        // Given
        val url = "test_url"
        val apiResponse = PokemonListResponse(0, "", "", listOf())
        coEvery { pokemonApi.getPreviousAndNextPokemonList(url) } returns apiResponse

        // When
        val result = pokemonRepository.getPreviousAndNextPokemonList(url)

        // Then
        assertEquals(apiResponse.toDomain(), result)
        coVerify { pokemonApi.getPreviousAndNextPokemonList(url) }
    }

    @Test
    fun `test getPokemonDetail`() = runBlockingTest {
        // Given
        val url = "test_url"
        val apiResponse = PokemonDetailResponse(
            id =0,
            name = "",
            height = 134,
            weight = 10,
            spritesWs = SpritesWs("")
        )
        coEvery { pokemonApi.getPokemonDetail(url) } returns apiResponse

        // When
        val result = pokemonRepository.getPokemonDetail(url)

        // Then
        assertEquals(apiResponse.toDomain(), result)
        coVerify { pokemonApi.getPokemonDetail(url) }
    }
}