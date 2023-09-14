package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.SearchTopbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SearchLaunchedEffect(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        SearchTopbar.buttons
            .onEach { button ->
                when (button) {
                    SearchTopbar.AppBarIcons.NavigationIcon -> {
                        navController.popBackStack()
                    }
                    SearchTopbar.AppBarIcons.Alarm -> {

                    }
                }
            }.launchIn(this)
    }
}