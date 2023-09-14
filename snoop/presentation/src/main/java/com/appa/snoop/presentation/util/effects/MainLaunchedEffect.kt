package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.MainTopbar
import com.appa.snoop.presentation.navigation.Router
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MainLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        MainTopbar.buttons
            .onEach { button ->
                when (button) {
                    MainTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                    MainTopbar.AppBarIcons.Search -> {
                        navController.navigate(Router.MAIN_SEARCH_ROUTER_NAME)
                    }
                    MainTopbar.AppBarIcons.Alarm -> {

                    }
                }
            }.launchIn(this)
    }
}