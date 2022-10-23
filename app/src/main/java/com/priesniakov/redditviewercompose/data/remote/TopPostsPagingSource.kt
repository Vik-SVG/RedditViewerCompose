package com.priesniakov.redditviewercompose.data.remote


import com.priesniakov.redditviewercompose.core.data.BasePagingDataSource
import com.priesniakov.redditviewercompose.data.entities.response.Children
import com.priesniakov.redditviewercompose.data.entities.response.RedditResponse


class TopPostsPagingSource(private val redditService: RedditApi) :
    BasePagingDataSource<RedditResponse, Children>(
        call = { key, limit ->
            redditService.getTopPostsResponse(key, limit)
        },
        onLoadSuccess = {
            LoadResult.Page(
                data = it.body()?.data?.children ?: listOf(),
                prevKey = it.body()?.data?.before, //reddit api always returns null
                nextKey = it.body()?.data?.after
            )
        }
    )

const val PAGE_SiZE = 10
const val PREFETCH_DISTANCE = 10
const val INITIAL_SIZE = 20
const val ENABLE_PLACEHOLDERS = false