package com.appa.snoop.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appa.snoop.presentation.ui.category.CategoryScreen
import com.appa.snoop.presentation.ui.home.HomeScreen
import com.appa.snoop.presentation.ui.like.LikeScreen
import com.appa.snoop.presentation.ui.login.LoginScreen
import com.appa.snoop.presentation.ui.mypage.MypageScreen
import com.appa.snoop.presentation.ui.mypage.profile.ModifyProfileScreen
import com.appa.snoop.presentation.ui.notification.NotificationScreen
import com.appa.snoop.presentation.ui.product.ProductDetailScreen
import com.appa.snoop.presentation.ui.search.SearchScreen
import com.appa.snoop.presentation.ui.signup.SignupScreen

@Composable
fun MainNavHost(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    showSnackBar: (String) -> Unit
) {
    val isLogined by remember { mutableStateOf(true) }

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
//            route = LoginNav.route
        ) {
//            if (isLogined) {
//                LikeScreen(navController)
//            } else {
                LoginScreen(
                    navController = navController,
                    showSnackBar = showSnackBar
                )
//                navController.navigate(Router.MAIN_LOGIN_ROUTER_NAME)
//            }
        }
        mainSlideTransitions(
            route = MainNav.MyPage.route,
        ) {
            if (isLogined) {
                MypageScreen(navController)
            } else {
                LoginScreen(
                    navController = navController,
                    showSnackBar = showSnackBar
                )
            }
        }
        defaultSlideTransitions(
            route = SearchNav.route,
        ) {
            SearchScreen(navController)
        }
        defaultSlideTransitions(
            route = LoginNav.route,
        ) {
            LoginScreen(
                navController = navController,
                showSnackBar = showSnackBar
            )
        }
        defaultSlideTransitions(
            route = NotificationNav.route
        ) {
            NotificationScreen(navController)
        }
        defaultSlideTransitions(
            route = Router.LOGIN_SIGNUP_ROUTER_NAME
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Router.LOGIN_SIGNUP_ROUTER_NAME)
            }
            SignupScreen(
                navController = navController,
                signupViewModel = hiltViewModel(parentEntry),
                showSnackBar
            )
        }
        defaultSlideTransitions(
            route = Router.CATEGORY_PRODUCT_ROUTER_NAME
        ) {
            ProductDetailScreen(
                navController = navController
            )
        }
        defaultSlideTransitions(
            route = ModifyProfileNav.route
        ) {
            ModifyProfileScreen(navController)
        }
    }
}