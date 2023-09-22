package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.LoginTopBar
import com.appa.snoop.presentation.common.topbar.component.NotificationTopbar
import com.appa.snoop.presentation.common.topbar.component.SignupTopBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NotificationLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        NotificationTopbar.buttons
            .onEach { button ->
                when (button) {
                    NotificationTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}