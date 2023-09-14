package com.appa.snoop.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appa.snoop.presentation.ui.category.MainCategoryScreen
import com.appa.snoop.presentation.ui.home.MainHomeScreen
import com.appa.snoop.presentation.ui.like.MainLikeScreen
import com.appa.snoop.presentation.ui.login.LoginScreen
import com.appa.snoop.presentation.ui.mypage.MainMypageScreen
import com.appa.snoop.presentation.ui.search.SearchScreen

@Composable
fun SnoopNavHost(
    innerPaddings: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = MainNav.Home.route
    ) {
        mainSlideTransitions(
            route = MainNav.Home.route
        ) {
            MainHomeScreen(navController)
        }
        mainSlideTransitions(
            route = MainNav.Category.route,
        ) {
            MainCategoryScreen(navController)
        }
        mainSlideTransitions(
            route = MainNav.Like.route,
        ) {
            MainLikeScreen(navController)
        }
        mainSlideTransitions(
            route = MainNav.MyPage.route,
        ) {
            MainMypageScreen(navController)
        }
        defaultSlideTransitions(
            route = SearchNav.route,
        ) {
            SearchScreen(navController)
        }
        defaultSlideTransitions(
            route = LoginNav.route,
        ) {
            LoginScreen(navController)
        }
    }
}