package org.example.project

import org.example.project.models.Hero

class DummySuperheroRepository : SuperheroRepository {
    override suspend fun getSuperheroes(name: String): List<Hero> {
        // Devuelve lista vacía porque todo se maneja en JS
        return emptyList()
    }
}

actual fun provideSuperheroRepository(): SuperheroRepository = DummySuperheroRepository()
