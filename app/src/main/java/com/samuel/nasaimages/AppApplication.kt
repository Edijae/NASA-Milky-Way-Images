package com.samuel.nasaimages

import com.samuel.nasaimages.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class AppApplication : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}