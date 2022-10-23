package com.priesniakov.redditviewercompose.data.remote

import com.priesniakov.redditviewercompose.data.entities.response.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    //the old value "top.json" isn't working
    //problem with reddit api

    @GET("r/popular.json")
    suspend fun getTopPostsResponse(
        @Query("after") after: String,
        @Query("limit") limit: String
    ): Response<RedditResponse>

}