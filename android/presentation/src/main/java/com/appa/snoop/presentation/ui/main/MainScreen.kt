package com.appa.snoop.presentation.ui.main

import android.util.Log
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.common.topbar.SharedTopAppBar
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.navigation.MainNav
import com.appa.snoop.presentation.navigation.MainNavHost
import kotlinx.coroutines.launch

private const val TAG = "[김희웅] MainScreen"
@Composable
fun MainScreen(
    type: String? = null,
    articleId: String? = null
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val appBarState = rememberAppBarState(navController = navController)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
            }
        )
        LaunchedEffect(Unit) {
            if (type != null && articleId != null) {
                Log.d("TEST", "MainScreen: PendingIntent 호출")
//                navController.navigate("${BoardDetailNav.route}/${type.lowercase(Locale.getDefault())}/$articleId")
            }
        }
    }
}