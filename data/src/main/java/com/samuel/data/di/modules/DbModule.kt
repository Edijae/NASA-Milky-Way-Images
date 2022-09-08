package com.samuel.data.di.modules


import android.content.Context
import androidx.room.Room
import com.samuel.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    fun getAppDb(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "Images"
        ).build()
    }
}