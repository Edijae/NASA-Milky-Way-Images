package com.samuel.nasaimages.viewmodels

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.samuel.domain.models.Image
import com.samuel.domain.repositories.ImagesRepository
import com.samuel.nasaimages.paging.ImagesPagingSource
import com.samuel.nasaimages.paging.ImagesRemoteMediator
import com.samuel.nasaimages.paging.InvalidationListener
import kotlinx.coroutines.flow.Flow
import util.AppIdlingResource
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var imagesRepository: ImagesRepository
    private var config = PagingConfig(pageSize = 15, initialLoadSize = 15)

    // The Idling Resource for testing purposes only with expresso.
    // visible only in development
    @Nullable
    private var mIdlingResource: AppIdlingResource? = null
    var page = 0

    //Use Pagination to fetch images. 15 images per page
    @OptIn(ExperimentalPagingApi::class)
    fun getImages(yearStart: Int, yearEnd: Int): Flow<PagingData<Image>> {
        val mediator = ImagesRemoteMediator(page, yearStart, yearEnd, imagesRepository)
        return Pager(
            config,
            remoteMediator = mediator
        ) {
            val source = ImagesPagingSource(imagesRepository)
            mediator.listener = object : InvalidationListener {
                override fun invalidate() {
                    source.invalidate()
                }
            }
            source
        }.flow.cachedIn(viewModelScope)
    }

    fun setIdleState(idle: Boolean) {
        mIdlingResource?.setIdleState(idle)
    }

    fun getIdlingResource(): AppIdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = AppIdlingResource()
        }
        return mIdlingResource
    }

}