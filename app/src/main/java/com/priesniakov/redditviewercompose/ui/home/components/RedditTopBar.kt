package com.priesniakov.redditviewercompose.ui.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.priesniakov.redditviewercompose.ui.theme.*

@Composable
fun RedditTopBar(currentScreen: Screen, allScreens: List<Screen>, onSelected: (Screen) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(), color = Mercury
    ) {
        val listState = rememberLazyListState()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = listState,
            contentPadding = PaddingValues(10.dp)
        ) {
            items(items = allScreens,
                key = { screen -> screen.route }) { screen ->
                RedditTabRow(
                    screen = screen,
                    currentScreen = currentScreen,
                    onSelected = onSelected
                )
            }
        }
    }
}

@Composable
private fun RedditTabRow(
    screen: Screen,
    currentScreen: Screen,
    onSelected: (Screen) -> Unit
) {
    val selected = screen == currentScreen

    val tabTextColor = White
    val tabBackgroundColor = Vermilion

    val durationMillis =
        if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration

    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) tabTextColor else Grey.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )

    val tabBackgroundTintColor by animateColorAsState(
        targetValue = if (selected) tabBackgroundColor else Mercury2,
        animationSpec = animSpec
    )
    Surface(
        color = tabBackgroundTintColor,
        elevation = 8.dp, shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize()
                .selectable(
                    selected = selected,
                    onClick = { onSelected(screen) },
                    role = Role.Tab,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = Dp.Unspecified,
                        color = Color.Unspecified
                    )
                )
                .clearAndSetSemantics { contentDescription = "" }
        ) {
            Spacer(Modifier.width(12.dp))
            Text(screen.title, color = tabTintColor)
            Spacer(Modifier.width(12.dp))
        }
    }
}

private val TabHeight = 46.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
