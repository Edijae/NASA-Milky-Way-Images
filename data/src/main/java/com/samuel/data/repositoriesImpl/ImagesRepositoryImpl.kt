package com.samuel.data.repositoriesImpl

import androidx.room.withTransaction
import com.samuel.data.database.AppDatabase
import com.samuel.data.models.Metadata
import com.samuel.data.services.ImagesService
import com.samuel.domain.models.Image
import com.samuel.domain.models.Result
import com.samuel.domain.repositories.ImagesRepository
import com.samuel.domain.utils.Utils
import javax.inject.Inject

internal class ImagesRepositoryImpl @Inject constructor(
    var imagesService: ImagesService,
    var db: AppDatabase
) :
    ImagesRepository {

    private val milkyWay = "milky way"

    override suspend fun totalImages(): Int {
        val metadata = db.imagesDao().totalImages()
        return metadata?.totalHits ?: 0
    }

    //Get images from the remove server.
    override suspend fun getImages(yearStart: Int, yearEnd: Int): Result {
        try {
            val response = imagesService.getImage(milkyWay, "image", yearStart, yearEnd)
            if (response.isSuccessful) {
                val collection = response.body()?.collection
                val images = (collection?.items ?: emptyList())
                val meta = collection?.metadata ?: Metadata()
                db.withTransaction {
                    db.imagesDao().clearAll()
                    // Insert new images into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.
                    db.imagesDao().insertAll(images)
                    db.imagesDao().insertMetadata(meta)
                }
                return Result(true, "")
            } else {
                return Result(false, "An error occurred. Please try again.")
            }
        } catch (exc: Exception) {
            return Result(false, "An error occurred. Please try again.")
        }
    }

    //load a max of 15 images from the local db using id.
    //this is to simulate pagination since the endpoint doesn't support pagination
    override suspend fun loadImages(pageNumber: Int): List<Image> {
        val start = if (pageNumber == 0) 1 else pageNumber
        val end = start + 14
        return db.imagesDao().loadImages(pageNumber, end).map {
            val data = it.data[0]
            val link = it.links[0]
            Image(
                it.id,
                data.album ?: emptyList(),
                data.center,
                data.title,
                data.keywords,
                data.location,
                data.nasaId,
                Utils.formatter.parse(data.dateCreated)
                    ?.let { it1 -> Utils.formatter.format(it1) },
                data.description,
                link.href
            )
        }
    }
}