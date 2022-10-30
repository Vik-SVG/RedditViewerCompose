package com.priesniakov.redditviewercompose.ui.home.top

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.priesniakov.redditviewercompose.data.Loading
import com.priesniakov.redditviewercompose.data.ResError
import com.priesniakov.redditviewercompose.data.Success
import com.priesniakov.redditviewercompose.data.entities.response.Children
import com.priesniakov.redditviewercompose.data.remote.*
import com.priesniakov.redditviewercompose.domain.RedditPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TopPostsViewModel @Inject constructor(
    private val useCase: RedditPostsUseCase,
    private val api: RedditApi
) : ViewModel() {

    private val _state = mutableStateOf(TopPostsState())
    val state: State<TopPostsState> = _state

    val topPostsPaging: Flow<PagingData<Children>> =
        Pager(PagingConfig(PAGE_SiZE, PREFETCH_DISTANCE, ENABLE_PLACEHOLDERS, INITIAL_SIZE)) {
            TopPostsPagingSource(api)
        }.flow.cachedIn(viewModelScope)

    fun getPosts() {
        useCase.getTopPosts("20", "").onEach { result ->
            _state.value = when (result) {
                Loading -> TopPostsState(isLoading = true)
                is ResError -> TopPostsState(error = result.message)
                is Success -> TopPostsState(posts = result.data.data.children)
            }
        }.launchIn(viewModelScope)
    }
}