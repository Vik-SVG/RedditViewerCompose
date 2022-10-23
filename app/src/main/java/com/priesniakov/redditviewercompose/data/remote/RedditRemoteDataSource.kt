package com.priesniakov.redditviewercompose.data.remote

import com.priesniakov.redditviewercompose.core.data.BaseDataSource
import com.priesniakov.redditviewercompose.data.Resource
import com.priesniakov.redditviewercompose.data.entities.response.RedditResponse
import javax.inject.Inject
import javax.inject.Singleton


interface RedditRemoteDataSource {
    suspend fun getTopPostsResource(after: String, limit: String): Resource<RedditResponse>
}

@Singleton
class RedditRemoteDataSourceImpl @Inject constructor(private val redditService: RedditApi) :
    BaseDataSource(),
    RedditRemoteDataSource {
    override suspend fun getTopPostsResource(after: String, limit: String) =
        getResults { redditService.getTopPostsResponse(after, limit) }
}