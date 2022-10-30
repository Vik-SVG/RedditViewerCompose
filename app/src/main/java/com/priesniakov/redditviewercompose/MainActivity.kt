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
import com.priesniakov.redditviewercompose.ui.home.components.RedditBottomBar
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
        val rootNavController = rememberNavController()
        val rootCurrentBackStack by rootNavController.currentBackStackEntryAsState()
        val currentRootDestination = rootCurrentBackStack?.destination
        var currentRootScreen by rememberSaveable {
            mutableStateOf(redditViewerScreens.find { it.route == currentRootDestination?.route }
                ?: HomeScreen)
        }

        var appBarTitle by rememberSaveable() {
            mutableStateOf(HomeScreen.title)
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopSearchBar(appBarTitle)
            },
            bottomBar = {
                RedditBottomBar(
                    currentScreen = currentRootScreen,
                    allScreens = redditRootScreens,
                    onSelected = {
                        appBarTitle = it.title
                        rootNavController.navigateSingleTopTo(it.route)
                        currentRootScreen = it
                    }
                )
            }) { outerPadding ->
            RedditAppNavHost(navController = rootNavController, Modifier.padding(outerPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RedditViewerApp()
}