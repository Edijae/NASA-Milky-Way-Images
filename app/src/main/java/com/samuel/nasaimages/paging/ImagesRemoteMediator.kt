package com.samuel.nasaimages.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samuel.domain.models.Image
import com.samuel.domain.repositories.ImagesRepository

/*Fetch images from the remote server and save them in the local db Then notify
paging source to fetch images from the local db
*/
@OptIn(ExperimentalPagingApi::class)
internal class ImagesRemoteMediator(
    private val page: Int,
    private val yearStart: Int,
    private val yearEnd: Int,
    private val imagesRepository: ImagesRepository
) : RemoteMediator<Int, Image>() {

    private var count: Int = 0
    var listener: InvalidationListener? = null

    override suspend fun initialize(): InitializeAction {
        count = imagesRepository.totalImages()

        return if (count == 0) {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Image>
    ): MediatorResult {
        // Suspending network load via Retrofit. This doesn't need to be
        // wrapped in a withContext(Dispatcher.IO) { ... } block since
        // Retrofit's Coroutine CallAdapter dispatches on a worker
        // thread.
        return if (loadType == LoadType.REFRESH) {
            val result = imagesRepository.getImages(yearStart, yearEnd)
            listener?.invalidate()
            if (result.success) {
                MediatorResult.Success(endOfPaginationReached = true)
            } else {
                MediatorResult.Error(Throwable(result.message))
            }

        } else {
            MediatorResult.Success(
                endOfPaginationReached = page >= count
            )
        }
    }
}

interface InvalidationListener {
    fun invalidate()
}
