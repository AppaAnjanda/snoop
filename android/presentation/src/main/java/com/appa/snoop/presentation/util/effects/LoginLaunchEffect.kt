package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.BackTopbar
import com.appa.snoop.presentation.common.topbar.component.LoginTopBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginLaunchEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        LoginTopBar.buttons
            .onEach { button ->
                when (button) {
                    LoginTopBar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}