package com.priesniakov.redditviewercompose.domain

import com.priesniakov.redditviewercompose.core.domain.BaseUseCase
import com.priesniakov.redditviewercompose.data.Resource
import com.priesniakov.redditviewercompose.data.entities.response.RedditResponse
import com.priesniakov.redditviewercompose.data.repository.RedditRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RedditPostsUseCase {
    fun getTopPosts(after: String, limit: String): Flow<Resource<RedditResponse>>
}

class RedditPostsUseCaseImpl @Inject constructor(private val repository: RedditRepository) :
    RedditPostsUseCase,
    BaseUseCase() {

    override fun getTopPosts(after: String, limit: String) =
        performNetworkCall { repository.getTopPostsResponse(after, limit) }

}