package org.example.project

import org.example.project.models.Hero

interface SuperheroRepository {
    suspend fun getSuperheroes(name: String): List<Hero>
}
expect fun provideSuperheroRepository(): SuperheroRepository

