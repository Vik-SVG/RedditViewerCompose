package com.priesniakov.redditviewercompose.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.priesniakov.redditviewercompose.ui.home.components.PostsListItem

@Composable
fun TopPostsScreen(
    onPostClick: () -> Unit,
    viewModel: TopPostsViewModel = hiltViewModel(),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val lazyItems = viewModel.topPostsPaging.collectAsLazyPagingItems()
        val refreshState = rememberSwipeRefreshState(isRefreshing = false)
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                if (!refreshState.isRefreshing)
                    lazyItems.refresh()
            }) {
            if (lazyItems.loadState.refresh !is LoadState.Loading)
            LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState()) {
                items(items = lazyItems, key = { it.data.id }) {
                    PostsListItem(onItemClick = onPostClick, post = it?.data)
                }
            }
        }
        when {
            lazyItems.loadState.refresh is LoadState.Loading -> ShowLoading()
            lazyItems.loadState.append is LoadState.Loading -> ShowLoading()
            lazyItems.loadState.append is LoadState.Error -> ShowError((lazyItems.loadState.append as? LoadState.Error)?.error)
            lazyItems.loadState.refresh is LoadState.Error -> ShowError((lazyItems.loadState.refresh as? LoadState.Error)?.error)
        }
    }
}


@Composable
private fun ShowLoading() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowError(error: Throwable?) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = error?.message ?: "Unknown Error",
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}