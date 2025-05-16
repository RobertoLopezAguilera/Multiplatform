package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
actual fun HeroImageView(url: String, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "Imagen del superhéroe",
        modifier = modifier
    )
}