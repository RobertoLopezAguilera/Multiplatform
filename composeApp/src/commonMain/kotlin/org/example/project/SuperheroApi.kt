package org.example.project.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import org.example.project.models.ApiResponse

interface SuperheroApi {

    @GET("api/{accessToken}/search/{name}")
    suspend fun searchHeroes(
        @Path("accessToken") accessToken: String,
        @Path("name") name: String
    ): ApiResponse
}
