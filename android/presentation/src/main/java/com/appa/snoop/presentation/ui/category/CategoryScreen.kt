package com.appa.snoop.presentation.ui.category

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.common.topbar.SharedTopAppBar
import com.appa.snoop.presentation.navigation.Router

import com.appa.snoop.presentation.ui.category.component.CategoryItem
import com.appa.snoop.presentation.ui.category.component.SnoopSearchBar
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.util.effects.CategoryLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import ir.kaaveh.sdpcompose.sdp

const val SIZE = 2
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit
) {
    CategoryLaunchedEffect(
        navController = navController,
        categoryViewModel = categoryViewModel
    )
    val scrollableState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        topBar = {
            AnimatedContent(
                targetState = categoryViewModel.searchBarState,
                transitionSpec = {
                     slideInVertically(
                         initialOffsetY = { -200 },
                         animationSpec = tween(200)
                     ) togetherWith slideOutVertically (
                         targetOffsetY = { -200 },
                         animationSpec = tween(200)
                     ) using SizeTransform(false)
                },
                label = ""
            ) { searchBarVisible ->
                if (searchBarVisible) {
                    SnoopSearchBar(
                        modifier = Modifier
                            .wrapContentHeight(),
                        focusManager = focusManager,
                        categoryViewModel = categoryViewModel,
                        showSnackBar = showSnackBar
                    )
                }
            }
//            if (categoryViewModel.searchBarState) {
//                SnoopSearchBar(
//                    modifier = Modifier
//                        .wrapContentHeight(),
//                    focusManager = focusManager,
//                    categoryViewModel = categoryViewModel,
//                    showSnackBar = showSnackBar
//                )
//            }
        },
    ) { paddingValue ->
        paddingValue
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(SIZE)
            ) {
                items(count = 10) {
                    CategoryItem(
                        modifier = Modifier,
                        onItemClicked = {
                            navController.navigate(Router.CATEGORY_PRODUCT_ROUTER_NAME)
                        },
                        onLikeClicked = { /*TODO*/ }
                    )
                }
            }
        }
    }
}