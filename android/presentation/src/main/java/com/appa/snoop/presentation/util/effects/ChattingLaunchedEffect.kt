package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.common.topbar.component.ChattingTopbar
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ChattingLaunchedEffect(
    navController: NavController,
) {
    LaunchedEffect(key1 = Unit) {
        ChattingTopbar.buttons
            .onEach { button ->
                when (button) {
                    ChattingTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                }
            }.launchIn(this)
    }
}