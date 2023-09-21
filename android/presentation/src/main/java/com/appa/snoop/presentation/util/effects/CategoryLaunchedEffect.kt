package com.appa.snoop.presentation.util.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CategoryLaunchedEffect(
    navController: NavController,
    categoryViewModel: CategoryViewModel
) {
    LaunchedEffect(key1 = Unit) {
        CategoryTopbar.buttons
            .onEach { button ->
                when (button) {
                    CategoryTopbar.AppBarIcons.ChatIcon -> {}
                    CategoryTopbar.AppBarIcons.SearchIcon -> {
                        categoryViewModel.searchBarToggle()
                    }
                }
            }.launchIn(this)
    }
}