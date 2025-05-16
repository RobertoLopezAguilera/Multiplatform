package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.models.Hero
import org.example.project.models.HeroImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SuperheroSearchApp()
}

@Composable
fun SuperheroSearchApp() {
    val repository = remember { provideSuperheroRepository() }

    var superheroName by remember { mutableStateOf("") }
    var superheroList by remember { mutableStateOf<List<Hero>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = superheroName,
                onValueChange = {
                    superheroName = it
                    errorMessage = null
                },
                label = { Text("Nombre del superhéroe") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                scope.launch {
                    try {
                        val result = repository.getSuperheroes(superheroName)
                        superheroList = result
                        errorMessage = if (result.isEmpty()) "No se encontraron resultados." else null
                    } catch (e: Exception) {
                        errorMessage = "Error al obtener los datos: ${e.message}"
                    }
                }
            }) {
                Text("Buscar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            LazyColumn {
                items(superheroList.size) { index ->
                    val hero = superheroList[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HeroImageView(
                                url = hero.image.url,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 16.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text("Nombre: ${hero.name}", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Altura: ${hero.appearance.height.joinToString()}")
                                Text("Peso: ${hero.appearance.weight.joinToString()}")
                                Text("Inteligencia: ${hero.powerstats.intelligence}")
                                Text("Fuerza: ${hero.powerstats.strength}")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
expect fun HeroImageView(url: String, modifier: Modifier = Modifier)