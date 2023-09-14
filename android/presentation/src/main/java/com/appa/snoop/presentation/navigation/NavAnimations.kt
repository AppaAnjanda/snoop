package com.appa.snoop.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.mainSlideTransitions(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(300),
//                initialAlpha = 0.5f
            )
//            slideIntoContainer(
//                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
//                animationSpec = tween(300)
//            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(100),
//                targetAlpha = 0.5f
            )
//            slideOutOfContainer(
//                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
//                animationSpec = tween(300)
//            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(300),
//                initialAlpha = 0.5f
            )
//            slideIntoContainer(
//                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
//                animationSpec = tween(300)
//            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300),
//                targetAlpha = 0.5f
            )
//            slideOutOfContainer(
//                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
//                animationSpec = tween(300)
//            )
        },
        content = content
    )
}

fun NavGraphBuilder.defaultSlideTransitions(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(300)
            )
        },
        content = content
    )
}