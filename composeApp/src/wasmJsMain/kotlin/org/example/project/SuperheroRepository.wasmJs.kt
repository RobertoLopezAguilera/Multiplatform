package org.example.project

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
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


interface SuperheroApiService {
    @GET("api.php/{access_token}/search/{name}")
    suspend fun searchHero(
        @Path("access_token") accessToken: String,
        @Path("name") name: String
    ): List<Hero>
}

actual fun provideSuperheroRepository(): SuperheroRepository = KtorfitSuperheroRepository()


