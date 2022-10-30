package com.priesniakov.redditviewercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.priesniakov.redditviewercompose.ui.home.components.RedditTopBar
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
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Home", style = TextAppBar)
                }
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