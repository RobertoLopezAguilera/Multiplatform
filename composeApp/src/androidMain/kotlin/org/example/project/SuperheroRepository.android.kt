package org.example.project

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.models.ApiResponse
import org.example.project.models.Hero

class KtorSuperheroRepository : SuperheroRepository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val ACCESS_TOKEN = "0e4e8170625bc10329009aabae104089"

    override suspend fun getSuperheroes(name: String): List<Hero> {
        val url = "https://www.superheroapi.com/api.php/$ACCESS_TOKEN/search/$name"
        val response: ApiResponse = httpClient.get(url).body()
        return response.results
    }
}

actual fun provideSuperheroRepository(): SuperheroRepository = KtorSuperheroRepository()
