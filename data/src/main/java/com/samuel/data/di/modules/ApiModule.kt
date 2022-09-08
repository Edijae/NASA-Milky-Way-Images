package com.samuel.data.di.modules


import com.samuel.data.services.ImagesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    internal fun getRateService(retrofit: Retrofit): ImagesService {
        return retrofit.create(ImagesService::class.java)
    }
}