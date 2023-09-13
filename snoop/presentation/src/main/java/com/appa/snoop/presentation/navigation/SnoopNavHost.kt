package com.appa.snoop.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appa.snoop.presentation.ui.category.MainCategoryScreen
import com.appa.snoop.presentation.ui.home.MainHomeScreen
import com.appa.snoop.presentation.ui.like.MainLikeScreen
import com.appa.snoop.presentation.ui.mypage.MainMypageScreen
import com.appa.snoop.presentation.ui.notification.NotificationScreen
import com.appa.snoop.presentation.ui.search.SearchScreen

@Composable
fun SnoopNavHost(
    innerPaddings: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = Router.MAIN_NOTIFICATION_ROUTER_NAME
    ) {
        composable(
            route = MainNav.Home.route,
        ) {
            MainHomeScreen()
        }
        composable(
            route = MainNav.Category.route,
        ) {
            MainCategoryScreen()
        }
        composable(
            route = MainNav.Like.route,
        ) {
            MainLikeScreen()
        }
        composable(
            route = MainNav.MyPage.route,
        ) {
            MainMypageScreen()
        }
        composable(
            route = SearchNav.route,
        ) {
            SearchScreen()
        }
        composable(
            route = Router.MAIN_NOTIFICATION_ROUTER_NAME,
        ) {
            NotificationScreen(navController = navController)
        }
    }
}