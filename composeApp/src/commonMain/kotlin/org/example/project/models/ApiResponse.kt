package org.example.project.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val results: List<Hero>,
    @SerialName("response") val ok: String
)

@Serializable
data class Hero(
    val id: String,
    val name: String,
    val powerstats: PowerStats,
    val biography: Biography,
    val appearance: Appearance
)

@Serializable
data class PowerStats(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)

@Serializable
data class Biography(
    @SerialName("full-name") val fullName: String,
    val alignment: String
)

@Serializable
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>
)