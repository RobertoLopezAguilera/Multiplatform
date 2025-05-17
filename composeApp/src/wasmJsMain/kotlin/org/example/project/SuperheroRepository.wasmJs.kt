package org.example.project

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.*
import io.ktor.client.engine.js.*
import org.example.project.models.Hero

class KtorfitSuperheroRepository : SuperheroRepository {

    private val token = "0e4e8170625bc10329009aabae104089"

    private val ktorfit = Ktorfit.Builder()
        .baseUrl("https://www.superheroapi.com/")
        .httpClient(HttpClient(Js)) // para WASM
        .converterFactories(HeroFactory())
        .build()

    //error
    //private val api = ktorfit.createSuperheroApiService()

    private val api = ktorfit.create<SuperheroApiService>()

    override suspend fun getSuperheroes(name: String): List<Hero> {
        return api.searchHero(token, name)
    }
}


interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Hero>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Hero

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): AuthResponse

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part("description") description: String,
        @Part("image") image: List<PartData>
    ): UploadResponse
}


actual fun provideSuperheroRepository(): SuperheroRepository = KtorfitSuperheroRepository()


