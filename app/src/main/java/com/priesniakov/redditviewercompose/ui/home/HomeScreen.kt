package com.priesniakov.redditviewercompose.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.priesniakov.redditviewercompose.ui.home.components.RedditTopBar
import com.priesniakov.redditviewercompose.ui.theme.*

@Composable
fun HomePageScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    var currentScreen by rememberSaveable {
        mutableStateOf(redditViewerScreens.find { it.route == currentDestination?.route }
            ?: TopPostsListScreen)
    }

    Scaffold(modifier = Modifier.padding(), topBar = {
        RedditTopBar(
            currentScreen = currentScreen,
            allScreens = redditScreensTopPanelScreens,
            onSelected = {
                navController.navigateSingleTopTo(it.route)
                currentScreen = it
            }
        )
    }) { innerPadding ->
        RedditViewerNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}