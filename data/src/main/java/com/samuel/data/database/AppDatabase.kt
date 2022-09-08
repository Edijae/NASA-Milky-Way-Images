package com.samuel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samuel.data.database.daos.ImagesDao
import com.samuel.data.models.ImageEntity
import com.samuel.data.models.Metadata

@Database(entities = [ImageEntity::class, Metadata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): AppDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, "Nasa.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    internal abstract fun imagesDao(): ImagesDao
}