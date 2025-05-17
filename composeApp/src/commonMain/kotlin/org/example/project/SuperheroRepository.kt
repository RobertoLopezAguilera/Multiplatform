package org.example.project

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.api.SuperheroApi
import org.example.project.models.Hero

class SuperheroRepository {

    private val ACCESS_TOKEN = "0e4e8170625bc10329009aabae104089"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private val ktorfit = Ktorfit.Builder()
        .baseUrl("https://www.superheroapi.com/")
        .httpClient(httpClient)
        .build()

    private val api = ktorfit.create<SuperheroApi>()

    suspend fun getSuperheroes(name: String): List<Hero> {
        val response = api.searchHeroes(ACCESS_TOKEN, name)
        return response.results
    }
}

