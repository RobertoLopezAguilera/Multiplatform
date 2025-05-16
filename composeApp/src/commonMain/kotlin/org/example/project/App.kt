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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SuperheroSearchApp()
}

@Composable
fun SuperheroSearchApp() {
    MaterialTheme {
        var superheroName by remember { mutableStateOf("") }
        var superheroList by remember { mutableStateOf<List<Hero>>(emptyList()) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

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
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = NetworkUtils.getSuperheroes(superheroName)
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
                    Column(Modifier.padding(8.dp)) {
                        Text("Nombre: ${hero.name}", style = MaterialTheme.typography.titleMedium)
                        Text("Imagen: ${hero.image}")
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
