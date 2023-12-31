package com.appa.snoop.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.category.component.CategoryItem
import com.appa.snoop.presentation.ui.home.common.HomeTitle
import com.appa.snoop.presentation.ui.home.component.AdsItem
import com.appa.snoop.presentation.ui.home.component.HomeItem
import com.appa.snoop.presentation.ui.home.component.HomeItemListTitleView
import com.appa.snoop.presentation.ui.home.component.MultipleImageView
import com.appa.snoop.presentation.ui.login.component.GoSignupText
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import com.appa.snoop.presentation.util.extensions.verticalScrollWithScrollbar
import ir.kaaveh.sdpcompose.sdp

private const val TAG = "HomeScreen_싸피"
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val scrollableState = rememberScrollState()
    val digitalList by homeViewModel.digitalList.collectAsState()
    val necessariesList by homeViewModel.necessariesList.collectAsState()
    val furnitureList by homeViewModel.furnitureList.collectAsState()
    val foodList by homeViewModel.foodList.collectAsState()
    val recommendList by homeViewModel.recommendList.collectAsState()

    MainLaunchedEffect(
        navController
    )
    LaunchedEffect(Unit) {
        homeViewModel.getRecommendProductList()
        homeViewModel.getPopularDigitalList()
        homeViewModel.getPopularNecessariesList()
        homeViewModel.getPopularFurnitureList()
        homeViewModel.getPopularFoodList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
//            .verticalScrollWithScrollbar(scrollableState)
//            .background(WhiteColor),
    ) {
        if (recommendList.isNotEmpty()) {
            AdsItem(
                Modifier.padding(8.sdp, 8.sdp),
                recommendList
            )
        }
        HomeItemListTitleView(
            titleName = HomeTitle.DIGITAL.titleName,
            titleImg = HomeTitle.DIGITAL.res
        )
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.sdp)
        ) {
            items(digitalList) {
                HomeItem(
                    modifier = Modifier,
                    product = it,
                    onItemClicked = {
                        Log.d(TAG, "HomeScreen: ${it.code}")
                        val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                            "{productCode}",
                            it.code
                        )
                        navController.navigate(route)
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.sdp)
        )
        HomeItemListTitleView(
            titleName = HomeTitle.NECESSARIES.titleName,
            titleImg = HomeTitle.NECESSARIES.res
        )
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.sdp)
        ) {
            items(necessariesList) {
                HomeItem(
                    modifier = Modifier,
                    product = it,
                    onItemClicked = {
                        val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                            "{productCode}",
                            it.code
                        )
                        navController.navigate(route)
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.sdp)
        )
        HomeItemListTitleView(
            titleName = HomeTitle.FURNITURE.titleName,
            titleImg = HomeTitle.FURNITURE.res
        )
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.sdp)
        ) {
            items(furnitureList) {
                HomeItem(
                    modifier = Modifier,
                    product = it,
                    onItemClicked = {
                        val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                            "{productCode}",
                            it.code
                        )
                        navController.navigate(route)
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.sdp)
        )
        HomeItemListTitleView(titleName = HomeTitle.FOOD.titleName, titleImg = HomeTitle.FOOD.res)
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.sdp)
        ) {
            items(foodList) {
                HomeItem(
                    modifier = Modifier,
                    product = it,
                    onItemClicked = {
                        val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                            "{productCode}",
                            it.code
                        )
                        navController.navigate(route)
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.sdp)
        )
    }
}