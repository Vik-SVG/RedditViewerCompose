package com.priesniakov.redditviewercompose.ui.theme

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priesniakov.redditviewercompose.R
import com.priesniakov.redditviewercompose.ui.home.HomePageScreen
import com.priesniakov.redditviewercompose.ui.home.top.TopPostsScreen
import com.priesniakov.redditviewercompose.ui.post_details.EmptyScreen
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

@Parcelize
object HomeScreen : Screen("Home", "home_screen"), Parcelable

@Parcelize
object ExploreScreen : Screen("Explore", "explore_screen"), Parcelable

@Parcelize
object ProfileScreen : Screen("Profile", "profile_screen"), Parcelable

@Parcelize
object MessagesScreen : Screen("Messages", "messages_screen"), Parcelable

@Parcelize
object MakePostScreen : Screen("Make post", "make_post_screen"), Parcelable

val redditViewerScreens = listOf(TopPostsListScreen, PostDetailScreen)

val redditScreensTopPanelScreens =
    listOf(TopPostsListScreen, AllPostsListScreen, CustomPostsListScreen, PopularPostsListScreen)

val redditRootScreens =
    listOf(HomeScreen, ExploreScreen, ProfileScreen, MessagesScreen, MakePostScreen)

val rootScreenIcons = mapOf(
    HomeScreen.route to R.drawable.ic_root_home,
    ExploreScreen.route to R.drawable.ic_explore,
    MessagesScreen.route to R.drawable.ic_baseline_message_24,
    MakePostScreen.route to R.drawable.ic_baseline_create_24,
    ProfileScreen.route to R.drawable.ic_baseline_person_24,
)


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
fun RedditAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = HomeScreen.route) {
            HomePageScreen()
        }
        composable(route = ExploreScreen.route) {
            EmptyScreen()
        }
        composable(route = MessagesScreen.route) {
            EmptyScreen()
        }
        composable(route = MakePostScreen.route) {
            EmptyScreen()
        }
        composable(route = ProfileScreen.route) {
            EmptyScreen()
        }
    }
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
            EmptyScreen()
        }
        composable(route = PopularPostsListScreen.route) {
            EmptyScreen()
        }
        composable(route = AllPostsListScreen.route) {
            EmptyScreen()
        }
        composable(route = CustomPostsListScreen.route) {
            EmptyScreen()
        }
    }
}
