package com.priesniakov.redditviewercompose.data.repository

import com.priesniakov.redditviewercompose.data.Resource
import com.priesniakov.redditviewercompose.data.entities.response.RedditResponse
import com.priesniakov.redditviewercompose.data.remote.RedditRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

interface RedditRepository {
    suspend fun getTopPostsResponse(
        after: String,
        limit: String
    ): Resource<RedditResponse>
}

@Singleton
class RedditRepositoryImp @Inject constructor(private val remoteDataSource: RedditRemoteDataSource, ) :
    RedditRepository {
    override suspend fun getTopPostsResponse(
        after: String,
        limit: String
    ): Resource<RedditResponse> = remoteDataSource.getTopPostsResource(after, limit)
}