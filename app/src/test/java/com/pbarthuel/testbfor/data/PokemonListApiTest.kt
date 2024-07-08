import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pbarthuel.testbfor.data.PokemonListApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class PokemonListApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: PokemonListApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        apiService = retrofit.create(PokemonListApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetPokemonListWithNullable() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "count": 1118,
                    "next": null,
                    "previous": null,
                    "results": [
                        {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"},
                        {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}
                    ]
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getPokemonList(0, 20)

        assertEquals(1118, response.count)
        assertEquals(null, response.next)
        assertEquals(null, response.previous)
        assertEquals(2, response.results.size)
        assertEquals("bulbasaur", response.results[0].name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/1/", response.results[0].url)
    }

    @Test
    fun testGetPokemonList() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "count": 1118,
                    "next": "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
                    "previous": "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
                    "results": [
                        {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"},
                        {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}
                    ]
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getPokemonList(0, 20)

        assertEquals(1118, response.count)
        assertEquals("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", response.next)
        assertEquals("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", response.previous)
        assertEquals(2, response.results.size)
        assertEquals("bulbasaur", response.results[0].name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/1/", response.results[0].url)
    }

    @Test
    fun testGetAllPokemonType() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "count": 20,
                    "next": "https://pokeapi.co/api/v2/type?offset=20&limit=20",
                    "previous": null,
                    "results": [
                        {"name": "normal", "url": "https://pokeapi.co/api/v2/type/1/"},
                        {"name": "fighting", "url": "https://pokeapi.co/api/v2/type/2/"}
                    ]
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getAllPokemonType(0, 20)

        assertEquals(20, response.count)
        assertEquals("https://pokeapi.co/api/v2/type?offset=20&limit=20", response.next)
        assertEquals(null, response.previous)
        assertEquals(2, response.results.size)
        assertEquals("normal", response.results[0].name)
        assertEquals("https://pokeapi.co/api/v2/type/1/", response.results[0].url)
    }

    @Test
    fun testGetPokemonListByType() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "pokemon": [
                        {"pokemon": {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"}},
                        {"pokemon": {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}}
                    ]
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val url = "/type/grass"
        val response = apiService.getPokemonListByType(mockWebServer.url(url).toString())

        assertEquals(2, response.pokemon.size)
        assertEquals("bulbasaur", response.pokemon[0].pokemon.name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/1/", response.pokemon[0].pokemon.url)
        assertEquals("ivysaur", response.pokemon[1].pokemon.name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/2/", response.pokemon[1].pokemon.url)
    }

    @Test
    fun testGetPreviousAndNextPokemonList() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "count": 1118,
                    "next": "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
                    "previous": "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20",
                    "results": [
                        {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"},
                        {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}
                    ]
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val url = "/pokemon?offset=20&limit=20"
        val response = apiService.getPreviousAndNextPokemonList(mockWebServer.url(url).toString())

        assertEquals(1118, response.count)
        assertEquals("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", response.next)
        assertEquals("https://pokeapi.co/api/v2/pokemon?offset=0&limit=20", response.previous)
        assertEquals(2, response.results.size)
        assertEquals("bulbasaur", response.results[0].name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/1/", response.results[0].url)
        assertEquals("ivysaur", response.results[1].name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/2/", response.results[1].url)
    }

    @Test
    fun testGetPokemonDetail() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "id": 1,
                    "name": "bulbasaur",
                    "height": 7,
                    "weight": 69,
                    "sprites": {
                        "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                    }
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")
        mockWebServer.enqueue(mockResponse)

        val url = "/pokemon/1"
        val response = apiService.getPokemonDetail(mockWebServer.url(url).toString())

        assertEquals(1, response.id)
        assertEquals("bulbasaur", response.name)
        assertEquals(7, response.height)
        assertEquals(69, response.weight)
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", response.sprites.frontDefault)
    }
}