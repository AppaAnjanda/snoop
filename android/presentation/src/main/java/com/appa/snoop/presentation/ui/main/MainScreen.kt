package com.appa.snoop.presentation.ui.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.common.topbar.SharedTopAppBar
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.navigation.MainNav
import com.appa.snoop.presentation.navigation.MainNavHost
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.mypage.MyPageViewModel
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

private const val TAG = "[김희웅] MainScreen"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    productCode: String? = null,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val appBarState = rememberAppBarState(navController = navController)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val mainViewModel: MainViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            if (appBarState.isVisible) {
                SharedTopAppBar(appBarState = appBarState)
            }
        },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                CustomTabBar(navController = navController, currentRoute = currentRoute)
            }
        },
    ) {
        MainNavHost(
            innerPaddings = it,
            navController = navController,
            showSnackBar = { message ->
                scope.launch {
                    snackBarHostState.showSnackbar(message)
                }
            },
            mainViewModel
        )
        LaunchedEffect(Unit) {
            if (productCode != null) {
                Log.d("TEST", "MainScreen: PendingIntent 호출")
                val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                    "{productCode}",
                    productCode
                )
                navController.navigate(route)
            }
        }
    }
}