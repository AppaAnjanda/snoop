package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.ProductTopbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ProductLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        ProductTopbar.buttons
            .onEach { button ->
                when (button) {
                    ProductTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}