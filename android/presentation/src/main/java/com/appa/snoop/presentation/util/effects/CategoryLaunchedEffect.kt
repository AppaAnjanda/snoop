package com.appa.snoop.presentation.util.effects

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.focus.FocusManager
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryLaunchedEffect(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    bottomSheetState: SheetState,
    focusManager: FocusManager
) {
    LaunchedEffect(key1 = Unit) {
        CategoryTopbar.buttons
            .onEach { button ->
                when (button) {
                    CategoryTopbar.AppBarIcons.ChatIcon -> {
                        navController.navigate(Router.CATEGORY_CHATTING_ROUTER_NAME)
                    }
                    CategoryTopbar.AppBarIcons.MenuIcon -> {
                    }
                }
            }.launchIn(this)
    }
}