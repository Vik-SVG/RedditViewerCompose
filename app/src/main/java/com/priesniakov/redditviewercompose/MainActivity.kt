package com.priesniakov.redditviewercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.priesniakov.redditviewercompose.ui.home.components.RedditTopBar
import com.priesniakov.redditviewercompose.ui.home.components.TopSearchBar
import com.priesniakov.redditviewercompose.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditViewerApp()
        }
    }
}

@Composable
private fun RedditViewerApp() {
    RedditViewerComposeTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        var currentScreen by rememberSaveable {
            mutableStateOf(redditViewerScreens.find { it.route == currentDestination?.route }
                ?: TopPostsListScreen)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopSearchBar()
            },
            bottomBar = {
                //Place for bottom bar (similar to top bar)
            }) { outerPadding ->
            Scaffold(
                modifier = Modifier.padding(outerPadding), topBar = {
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RedditViewerApp()
}