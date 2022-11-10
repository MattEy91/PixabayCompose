package de.matthiaseymers.pixabaycompose.data.remote

import de.matthiaseymers.pixabaycompose.BuildConfig
import de.matthiaseymers.pixabaycompose.data.remote.dto.SearchHitDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/")
    suspend fun searchHitsAsync(@Query("key") apiKey: String = BuildConfig.API_KEY, @Query("q") query: String): SearchHitDto
}