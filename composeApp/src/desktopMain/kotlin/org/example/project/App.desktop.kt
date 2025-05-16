package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.InputStream
import java.net.URL

@Composable
actual fun HeroImageView(url: String, modifier: Modifier) {
    val imageBitmap: ImageBitmap? = remember(url) {
        runCatching {
            val inputStream: InputStream = URL(url).openStream()
            val bytes = inputStream.readBytes()
            val skiaImage = Image.makeFromEncoded(bytes)
            skiaImage.toComposeImageBitmap()
        }.getOrNull()
    }

    imageBitmap?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = "Imagen del superhéroe",
            modifier = modifier
        )
    }
}
