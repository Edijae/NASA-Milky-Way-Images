package com.samuel.data.services

import com.samuel.data.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesService {
    @GET("search")
    suspend fun getImage(
        @Query("q") query: String,
        @Query("media_type") mediaType: String,
        @Query("year_start") yearStart: Int,
        @Query("year_end") yearEnd: Int,
    ): Response<ImageResponse>
}