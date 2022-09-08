package com.samuel.data.di.modules

import com.samuel.data.database.AppDatabase
import com.samuel.data.repositoriesImpl.ImagesRepositoryImpl
import com.samuel.data.services.ImagesService
import com.samuel.domain.repositories.ImagesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun getImagesRepository(
        imagesService: ImagesService,
        appDatabase: AppDatabase
    ): ImagesRepository {
        return ImagesRepositoryImpl(imagesService, appDatabase)
    }
}