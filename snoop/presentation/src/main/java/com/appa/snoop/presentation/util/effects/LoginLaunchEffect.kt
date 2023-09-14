package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.OnlyTextTopbar
import com.appa.snoop.presentation.common.topbar.component.SearchTopbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginLaunchEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        OnlyTextTopbar.buttons
            .onEach { button ->
                when (button) {
                    OnlyTextTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}