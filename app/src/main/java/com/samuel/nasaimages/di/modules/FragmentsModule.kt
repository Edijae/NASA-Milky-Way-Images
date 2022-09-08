package com.samuel.nasaimages.di.modules

import androidx.fragment.app.FragmentFactory
import com.samuel.nasaimages.di.DaggerFragmentFactory
import com.samuel.nasaimages.ui.fragments.ImageFragment
import com.samuel.nasaimages.ui.fragments.MainFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @Binds
    abstract fun bindFragmentFactory(
        factory: DaggerFragmentFactory
    ): FragmentFactory

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeImagesFragment(): ImageFragment

}