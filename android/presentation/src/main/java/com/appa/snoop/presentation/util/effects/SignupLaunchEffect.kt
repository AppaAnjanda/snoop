package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.BackTopbar
import com.appa.snoop.presentation.common.topbar.component.LoginTopBar
import com.appa.snoop.presentation.common.topbar.component.SignupTopBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignupLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        SignupTopBar.buttons
            .onEach { button ->
                when (button) {
                    SignupTopBar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}