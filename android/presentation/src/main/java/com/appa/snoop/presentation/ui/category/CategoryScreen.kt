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
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.component.BottomSheetItem
import com.appa.snoop.presentation.ui.category.component.CategoryBottomSheet
import com.appa.snoop.presentation.ui.category.component.CategoryItem
import com.appa.snoop.presentation.ui.category.component.PriceRangeView
import com.appa.snoop.presentation.ui.category.component.SnoopSearchBar
import com.appa.snoop.presentation.ui.home.dumy.itemList
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor_70
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.CategoryLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    val scrollState = rememberScrollState()
    val scrollableState = rememberScrollableState{ 1f }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val pagingData = categoryViewModel.pagingDataFlow.collectAsLazyPagingItems()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

//    CategoryLaunchedEffect(
//        navController = navController,
//        categoryViewModel = categoryViewModel,
//        bottomSheetState = sheetState,
//        focusManager = focusManager
//    )

    LaunchedEffect(key1 = Unit) {
        CategoryTopbar.buttons
            .onEach { button ->
                when (button) {
                    CategoryTopbar.AppBarIcons.ChatIcon -> {
                        navController.navigate(Router.CATEGORY_CHATTING_ROUTER_NAME)
                    }
                    CategoryTopbar.AppBarIcons.MenuIcon -> {
                        if (drawerState.isOpen)
                            drawerState.close()
                        else
                            drawerState.open()
                    }
                }
            }.launchIn(this)
    }

    LaunchedEffect(pagingData, categoryViewModel.keywordSearchState) {
        if (pagingData.itemCount == 0 && categoryViewModel.keywordSearchState > 0) {
            showSnackBar("검색 결과가 없습니다.")
        }
    }

    LaunchedEffect(drawerState.isOpen) {
        if (!drawerState.isOpen) {
            focusManager.clearFocus()
        }
    }

    ModalNavigationDrawer(
        modifier = Modifier
            .addFocusCleaner(focusManager),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(260.sdp),
                drawerTonalElevation = 0.sdp
            ) {
                Column(
                    modifier = Modifier
                        .background(WhiteColor)
                        .fillMaxSize()
                ) {
                    SnoopSearchBar(
                        modifier = Modifier
                            .wrapContentHeight(),
                        focusManager = focusManager,
                        categoryViewModel = categoryViewModel,
                        showSnackBar = showSnackBar
                    )
                    ClickableButton(
                        onClick = {
                            focusManager.clearFocus()
                            categoryViewModel.priceRangeStateToggle()
                        },
                        modifier = Modifier
                            .padding(start = 16.sdp, top = 16.sdp),
                        buttonColor = if (categoryViewModel.priceRangeState) DarkGrayColor else PrimaryColor
                    ) {
                        Row {
                            Text(
                                text = "가격 범위 정하기",
                                fontSize = 10.ssp
                            )
                        }
                    }
                    AnimatedContent(
                        targetState = categoryViewModel.priceRangeState,
                        transitionSpec = {
                            scaleIn(
                                transformOrigin = TransformOrigin(0.5f, -1f)
                            ) togetherWith scaleOut(
                                transformOrigin = TransformOrigin(0.5f, -1f)
                            )
                        },
                        label = ""
                    ) { it ->
                        if (it) {
                            PriceRangeView(
                                modifier = Modifier
                                    .padding(start = 16.sdp, end = 16.sdp)
                                    .wrapContentHeight(),
                                focusManager = focusManager,
                                categoryViewModel = categoryViewModel
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.sdp)
                    ) {
                        for (it in CategoryList.list) {
                            BottomSheetItem(
                                majorName = it.majorName,
                                categoryViewModel = categoryViewModel,
                                categoryState = when(it.majorName) {
                                    "디지털가전" -> categoryViewModel.digitalCategoryState
                                    "가구" -> categoryViewModel.furnitureCategoryState
                                    "생활용품" -> categoryViewModel.necessariesCategoryState
                                    "식품" -> categoryViewModel.foodCategoryState
                                    else -> false
                                },
                                minorList = it.minorList,
                                onClick = {
                                    categoryViewModel.sheetSelect(it.majorName)
                                },
                                onDismiss = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        ) { paddingValue ->
            paddingValue
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (pagingData.itemCount == 0) {
                    Text(
                        text = "기웃기웃의 둘러보기 기능입니다.",
                        fontSize = 18.ssp
                    )
                    Spacer(modifier = Modifier.height(12.sdp))
                    Text(
                        text = "카테고리 & 검색 기능을 이용해주세요.",
                        fontSize = 14.ssp
                    )
                    Spacer(modifier = Modifier.height(12.sdp))
                    Image(
                        modifier = Modifier
                            .size(200.sdp),
                        painter = painterResource(id = R.drawable.img_meerkat_face),
                        contentDescription = null
                    )
                } else {
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
                                    navController.navigate(Router.CATEGORY_PRODUCT_ROUTER_NAME)
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
    }

//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .addFocusCleaner(focusManager),
//        topBar = {
//            AnimatedContent(
//                targetState = categoryViewModel.searchBarState,
//                transitionSpec = {
//                     slideInVertically(
//                         initialOffsetY = { -200 },
//                         animationSpec = tween(200)
//                     ) togetherWith slideOutVertically (
//                         targetOffsetY = { -200 },
//                         animationSpec = tween(200)
//                     ) using SizeTransform(false)
//                },
//                label = ""
//            ) { searchBarVisible ->
//                if (searchBarVisible) {
//                    SnoopSearchBar(
//                        modifier = Modifier
//                            .wrapContentHeight(),
//                        focusManager = focusManager,
//                        categoryViewModel = categoryViewModel,
//                        showSnackBar = showSnackBar
//                    )
//                }
//            }
//        },
//    ) { paddingValue ->
//        paddingValue
//        Column(
//            modifier = modifier
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (pagingData.itemCount == 0) {
////                DrawerView()
//                Text(
//                    text = "기웃기웃의 둘러보기 기능입니다.",
//                    fontSize = 18.ssp
//                )
//                Spacer(modifier = Modifier.height(12.sdp))
//                Text(
//                    text = "카테고리 & 검색 기능을 이용해주세요.",
//                    fontSize = 14.ssp
//                )
//                Spacer(modifier = Modifier.height(12.sdp))
//                Image(
//                    modifier = Modifier
//                        .size(200.sdp),
//                    painter = painterResource(id = R.drawable.img_meerkat_face),
//                    contentDescription = null
//                )
//            } else {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(SIZE),
//                ) {
//                    items(
//                        pagingData.itemCount,
//                        key = {
//                            pagingData[it]!!.id
//                        }
//                    ) {
//                        CategoryItem(
//                            modifier = Modifier,
//                            product = pagingData[it]!!,
//                            onItemClicked = {
//                                navController.navigate(Router.CATEGORY_PRODUCT_ROUTER_NAME)
//                            },
//                            onLikeClicked = {
//                                // TODO 구현 찜 토글
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
}