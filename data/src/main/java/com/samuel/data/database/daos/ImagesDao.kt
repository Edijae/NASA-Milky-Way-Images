package com.samuel.data.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuel.data.models.ImageEntity
import com.samuel.data.models.Metadata

@Dao
internal interface ImagesDao {

    @Query("SELECT * FROM ImageEntity")
    fun getAll(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query("SELECT * FROM ImageEntity")
    fun pagingSource(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM ImageEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM Metadata WHERE id == 0 LIMIT 1")
    suspend fun totalImages(): Metadata?

    @Query("SELECT * FROM ImageEntity WHERE id BETWEEN :from AND :to")
    suspend fun loadImages(from: Int, to: Int): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetadata(metadata: Metadata)
}