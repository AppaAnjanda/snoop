package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.common.topbar.component.ModifyProfileTopbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ModifyProfileLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        ModifyProfileTopbar.buttons
            .onEach { button ->
                when (button) {
                    ModifyProfileTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}