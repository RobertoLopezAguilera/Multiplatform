package org.example.project

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.example.project.models.ApiResponse
import org.example.project.models.Hero

class HeroFactory : Converter.Factory {
    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        // Solo aplicar si el tipo de retorno es List<Hero>
        if (typeData.typeInfo.type == List::class) {
            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                override suspend fun convert(result: KtorfitResult): Any {
                    val bodyText = (result as KtorfitResult.Success).response.bodyAsText()
                    val envelope = Json.decodeFromString<ApiResponse>(bodyText)
                    return envelope.results
                }
            }
        }
        return null
    }
}
