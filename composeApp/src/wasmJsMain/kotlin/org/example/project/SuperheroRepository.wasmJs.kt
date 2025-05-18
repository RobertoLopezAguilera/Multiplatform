package org.example.project

import kotlinx.coroutines.CompletableDeferred
import org.example.project.models.Hero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.example.project.models.ApiResponse

class JsSuperheroRepository : SuperheroRepository {
    override suspend fun getSuperheroes(name: String): List<Hero> {
        val deferred = CompletableDeferred<List<Hero>>()
        HeroResultHolder.setDeferred(deferred)

        fetchHeroes(name)  // en lugar de js("fetchHeroes(name)")

        return deferred.await()
    }

}

object HeroResultHolder {
    private var deferred: CompletableDeferred<List<Hero>>? = null

    fun setDeferred(newDeferred: CompletableDeferred<List<Hero>>) {
        deferred = newDeferred
    }

    fun provideResult(json: String) {
        try {
            val apiResponse = Json.decodeFromString<ApiResponse>(json)
            deferred?.complete(apiResponse.results)
        } catch (e: Throwable) {
            println("Error al parsear JSON desde JS: ${e.message}")
            deferred?.complete(emptyList())
        } finally {
            deferred = null
        }
    }

}

object HeroBridge {
    @JsName("sendHeroesFromJs")
    fun sendHeroesFromJs(jsonString: String) {
        HeroResultHolder.provideResult(jsonString)
    }
}

external fun fetchHeroes(name: String)

actual fun provideSuperheroRepository(): SuperheroRepository = JsSuperheroRepository()
