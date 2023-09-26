package com.appa.snoop.presentation.ui.category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.component.CategoryBottomSheet
import com.appa.snoop.presentation.ui.category.component.CategoryItem
import com.appa.snoop.presentation.ui.category.component.SnoopSearchBar
import com.appa.snoop.presentation.ui.home.dumy.itemList
import com.appa.snoop.presentation.util.effects.CategoryLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

private const val TAG = "[김희웅] CategoryScreen"
const val SIZE = 2

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scrollableState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val pagingData = categoryViewModel.pagingDataFlow.collectAsLazyPagingItems()

    CategoryLaunchedEffect(
        navController = navController,
        categoryViewModel = categoryViewModel,
        bottomSheetState = sheetState,
        focusManager = focusManager
    )

    if (sheetState.isVisible) {
        CategoryBottomSheet(
            categoryViewModel = categoryViewModel,
            sheetState = sheetState,
        ) {
            scope.launch {
                sheetState.hide()
            }
        }
    }


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
                    ) togetherWith slideOutVertically(
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
        },
    ) { paddingValue ->
        paddingValue
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            // TODO 페이징 기능 구현하기
            LazyVerticalGrid(
                columns = GridCells.Fixed(SIZE),
            ) {
                items(
                    pagingData.itemCount,
                    key = {
                        pagingData[it]!!.id
                    }
                ) {
                    CategoryItem(
                        modifier = Modifier,
                        product = pagingData[it]!!,
                        onItemClicked = {
                            val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                                "{productCode}",
                                pagingData[it]!!.code
                            )
                            navController.navigate(route)
                        },
                        onLikeClicked = {
                            // TODO 구현 찜 토글
                        }
                    )
                }
            }
        }
    }
}