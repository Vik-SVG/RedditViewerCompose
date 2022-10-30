package com.priesniakov.redditviewercompose.ui.home.top

import com.priesniakov.redditviewercompose.data.entities.response.Children

data class TopPostsState(
    val isLoading: Boolean = false,
    val posts: List<Children> = emptyList(),
    val error: String = ""
)