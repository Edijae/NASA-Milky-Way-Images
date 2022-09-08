package com.samuel.nasaimages.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samuel.domain.models.Image
import com.samuel.domain.repositories.ImagesRepository

//Load images from the local db. 15 per page
internal class ImagesPagingSource(private val repository: ImagesRepository) :
    PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        val initialLoadSize = state.config.initialLoadSize
        var key: Int? = null
        state.anchorPosition?.let {
            key = 0.coerceAtLeast(it - initialLoadSize / 2)
        }
        return key
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val index = params.key ?: 0
        val result = repository.loadImages(index)
        val prevKey = if (index > 1) index - 1 else null
        val next = if (result.isNotEmpty()) index + result.size + 1 else null
        return LoadResult.Page(data = result, prevKey, next)
    }
}