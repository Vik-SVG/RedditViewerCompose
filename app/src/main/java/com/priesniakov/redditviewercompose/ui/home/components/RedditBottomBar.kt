package com.priesniakov.redditviewercompose.ui.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.priesniakov.redditviewercompose.R
import com.priesniakov.redditviewercompose.ui.theme.*

@Composable
fun RedditBottomBar(currentScreen: Screen, allScreens: List<Screen>, onSelected: (Screen) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(), color = Mercury
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            allScreens.forEachIndexed { index, screen ->
                BottomTab(screen, currentScreen, onSelected)
            }
        }
    }
}

@Composable
fun BottomTab(screen: Screen, currentScreen: Screen, onSelected: (Screen) -> Unit) {
    val selected = screen == currentScreen

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

    val iconTintColor by animateColorAsState(
        targetValue = if (selected) tabBackgroundColor else Mercury2,
        animationSpec = animSpec
    )
    Icon(
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
            .clearAndSetSemantics { contentDescription = "" },
        painter = painterResource(id = rootScreenIcons.getValue(screen.route)),
        contentDescription = "", tint = iconTintColor
    )
}

private const val InactiveTabOpacity = 0.60f
private val TabHeight = 46.dp

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100