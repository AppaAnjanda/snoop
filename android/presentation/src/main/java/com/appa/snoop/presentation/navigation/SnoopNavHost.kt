package com.appa.snoop.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appa.snoop.presentation.ui.category.CategoryScreen
import com.appa.snoop.presentation.ui.home.HomeScreen
import com.appa.snoop.presentation.ui.like.LikeScreen
import com.appa.snoop.presentation.ui.login.LoginScreen
import com.appa.snoop.presentation.ui.mypage.MypageScreen
import com.appa.snoop.presentation.ui.mypage.profile.ModifyProfileScreen
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
        startDestination = MainNav.Home.route
    ) {
        mainSlideTransitions(
            route = MainNav.Home.route
        ) {
            HomeScreen(navController)
        }
        mainSlideTransitions(
            route = MainNav.Category.route,
        ) {
            CategoryScreen(
                navController = navController
            )
        }
        mainSlideTransitions(
            route = MainNav.Like.route,
        ) {
            LikeScreen(navController)
        }
        mainSlideTransitions(
            route = MainNav.MyPage.route,
        ) {
            MypageScreen(navController)
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
        defaultSlideTransitions(
            route = NotificationNav.route
        ) {
            NotificationScreen(navController)
        }
        defaultSlideTransitions(
            route = ModifyProfileNav.route
        ) {
            ModifyProfileScreen(navController)
        }
    }
}