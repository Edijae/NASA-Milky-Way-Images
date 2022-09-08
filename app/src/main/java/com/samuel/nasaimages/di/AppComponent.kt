package com.samuel.nasaimages.di

import android.content.Context
import com.samuel.data.di.modules.ApiModule
import com.samuel.data.di.modules.DbModule
import com.samuel.data.di.modules.RepositoriesModule
import com.samuel.data.di.modules.RetrofitModule
import com.samuel.nasaimages.AppApplication
import com.samuel.nasaimages.di.modules.ActivitiesModule
import com.samuel.nasaimages.di.modules.FragmentsModule
import com.samuel.nasaimages.di.modules.ViewModelsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

//Dependency injection using dagger
@Component(
    modules = [
        DbModule::class, RetrofitModule::class, ApiModule::class,
        RepositoriesModule::class,
        ViewModelsModule::class,
        AndroidInjectionModule::class,
        ActivitiesModule::class,
        FragmentsModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
