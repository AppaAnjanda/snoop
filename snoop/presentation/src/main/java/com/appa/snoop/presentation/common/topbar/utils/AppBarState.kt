package com.appa.snoop.presentation.common.topbar.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.Topbars
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "AppBarState_싸피"
@Stable
class AppBarState(
    navController: NavController,
    scope: CoroutineScope
) {
    init {
        navController.currentBackStackEntryFlow
            .distinctUntilChanged()
            .onEach { backStackEntry ->
                val route = backStackEntry.destination.route
                Log.d(TAG, ": $route")
                currentScreen = getTopbar(route)
            }
            .launchIn(scope)
    }

    val isCenterTopBar: Boolean
        @Composable get() = currentScreen?.isCenterTopBar == true

    var currentScreen by mutableStateOf<Topbars?>(null)
        private set

    val isVisible: Boolean
        @Composable get() = currentScreen?.isAppBarVisible == true

    val navigationIcon: Int?
        @Composable get() = currentScreen?.navigationIcon

    val navigationIconContentDescription: String?
        @Composable get() = currentScreen?.navigationIconContentDescription

    val onNavigationIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.onNavigationIconClick

    val title: Any?
//        @Composable get() = currentScreen?.title.orEmpty()
        @Composable get() = currentScreen?.title

    val actions: List<ActionMenuItem>
        @Composable get() = currentScreen?.actions.orEmpty()
}

@Composable
fun rememberAppBarState(
    navController: NavController,
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    AppBarState(
        navController,
        scope,
    )
}