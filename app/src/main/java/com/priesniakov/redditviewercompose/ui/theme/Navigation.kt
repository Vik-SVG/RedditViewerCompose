package com.priesniakov.redditviewercompose.ui.theme

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priesniakov.redditviewercompose.ui.home.TopPostsScreen
import com.priesniakov.redditviewercompose.ui.post_details.PostDetails
import kotlinx.parcelize.Parcelize

sealed class Screen(val title: String, val route: String) : Parcelable

@Parcelize
object TopPostsListScreen : Screen("Top Posts", "top_posts_list_screen"), Parcelable

@Parcelize
object PostDetailScreen : Screen("Post", "post_detail_screen"), Parcelable

@Parcelize
object PopularPostsListScreen : Screen("Popular", "popular_posts_list_screen"), Parcelable

@Parcelize
object AllPostsListScreen : Screen("All", "all_posts_list_screen"), Parcelable

@Parcelize
object CustomPostsListScreen : Screen("Custom", "custom_posts_list_screen"), Parcelable

val redditViewerScreens = listOf(TopPostsListScreen, PostDetailScreen)
val redditScreensTopPanelScreens =
    listOf(TopPostsListScreen, AllPostsListScreen, CustomPostsListScreen, PopularPostsListScreen)


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
        composable(route = PopularPostsListScreen.route) {
            PostDetails()
        }
        composable(route = AllPostsListScreen.route) {
            PostDetails()
        }
        composable(route = CustomPostsListScreen.route) {
            PostDetails()
        }
    }
}
