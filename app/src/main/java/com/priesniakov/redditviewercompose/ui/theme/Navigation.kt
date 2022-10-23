package com.priesniakov.redditviewercompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priesniakov.redditviewercompose.ui.home.TopPostsScreen
import com.priesniakov.redditviewercompose.ui.post_details.PostDetails

sealed class Screen(val route: String)
object TopPostsListScreen : Screen("top_posts_list_screen")
object PostDetailScreen : Screen("post_detail_screen")

val redditViewerScreens = listOf(TopPostsListScreen, PostDetailScreen)


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToDetailsScreen() {
    this.navigateSingleTopTo("${PostDetailScreen.route}/")
}

@Composable
fun RedditViewerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TopPostsListScreen.route,
        modifier = modifier
    ) {
        composable(route = TopPostsListScreen.route) {
            TopPostsScreen(onPostClick = { navController.navigateSingleTopTo(PostDetailScreen.route) })
        }
        composable(route = PostDetailScreen.route) {
            PostDetails()
        }
    }
}
