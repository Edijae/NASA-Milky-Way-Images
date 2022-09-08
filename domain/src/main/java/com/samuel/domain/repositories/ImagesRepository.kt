package com.samuel.domain.repositories

import com.samuel.domain.models.Image
import com.samuel.domain.models.Result

interface ImagesRepository {
    suspend fun getImages(yearStart: Int, yearEnd: Int): Result
    suspend fun totalImages(): Int
    suspend fun loadImages(pageNumber: Int): List<Image>
}