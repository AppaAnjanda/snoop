package com.appa.snoop.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appa.snoop.presentation.ui.category.CategoryScreen
import com.appa.snoop.presentation.ui.chatting.ChattingScreen
import com.appa.snoop.presentation.ui.home.HomeScreen
import com.appa.snoop.presentation.ui.like.LikeScreen
import com.appa.snoop.presentation.ui.login.LoginScreen
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.mypage.MypageScreen
import com.appa.snoop.presentation.ui.mypage.modifyprofile.ModifyProfileScreen
import com.appa.snoop.presentation.ui.notification.NotificationScreen
import com.appa.snoop.presentation.ui.product.ProductDetailScreen
import com.appa.snoop.presentation.ui.search.SearchScreen
import com.appa.snoop.presentation.ui.signup.SignupScreen

private const val TAG = "[김희웅] MainNavHost"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavHost(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    showSnackBar: (String) -> Unit,
    mainViewModel: MainViewModel
) {
    mainViewModel.getLoginStatus()

    NavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = MainNav.Home.route
    ) {
        mainSlideTransitions(
            route = MainNav.Home.route
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Router.MAIN_HOME_ROUTER_NAME)
            }
            HomeScreen(
                navController,
                homeViewModel = hiltViewModel(parentEntry)
            )
        }
        mainSlideTransitions(
            route = MainNav.Category.route,
        ) {
            CategoryScreen(
                navController = navController,
                showSnackBar = showSnackBar,
                mainViewModel = mainViewModel
            )
        }
        mainSlideTransitions(
            route = MainNav.Like.route,
//            route = LoginNav.route
        ) {
            Log.d(TAG, "MainNavHost: 중복 스크린입니다...")
            if (mainViewModel.loginState) {
                LikeScreen(
                    navController = navController,
                    showSnackBar = showSnackBar
                )
            } else {
                LoginScreen(
                    navController = navController,
                    showSnackBar = showSnackBar
                )
//                navController.navigate(Router.MAIN_LOGIN_ROUTER_NAME)
            }
        }
        mainSlideTransitions(
            route = MainNav.MyPage.route,
        ) {
            if (mainViewModel.loginState) {
                MypageScreen(
                    navController = navController,
                    showSnackBar = showSnackBar,
                    mainViewModel = mainViewModel
                )
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
                showSnackBar = showSnackBar,
                navController = navController
            )
        }
        defaultSlideTransitions(
            route = ModifyProfileNav.route
        ) {
            ModifyProfileScreen(
                navController = navController,
                showSnackBar = showSnackBar,
                mainViewModel = mainViewModel
            )
        }
        defaultSlideTransitions(
            route = Router.CATEGORY_CHATTING_ROUTER_NAME
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Router.CATEGORY_CHATTING_ROUTER_NAME)
            }
            ChattingScreen(
                navController = navController,
                chattingViewModel = hiltViewModel(parentEntry),
                mainViewModel = mainViewModel
            )
        }
    }
}