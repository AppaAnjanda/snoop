package com.appa.snoop.presentation.ui.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.home.component.HomeItem
import com.appa.snoop.presentation.ui.home.component.HomeItemListTitleView
import com.appa.snoop.presentation.ui.home.component.MultipleImageView
import com.appa.snoop.presentation.ui.home.dumy.imageLinks
import com.appa.snoop.presentation.ui.home.dumy.imageLinksToCoupang
import com.appa.snoop.presentation.ui.home.dumy.itemList
import com.appa.snoop.presentation.ui.login.component.GoSignupText
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import ir.kaaveh.sdpcompose.sdp

@Composable
fun HomeScreen(
    navController: NavController
) {
    val scrollableState = rememberScrollState()
    var isClicked by remember { mutableStateOf(false) }

    MainLaunchedEffect(navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
//            .background(WhiteColor),
    ) {
        MultipleImageView(
            Modifier.padding(8.sdp, 8.sdp),
            imageLinks = imageLinks,
            imageLinksToCoupang = imageLinksToCoupang
        )

        for (i in 0 .. 6) {
            HomeItemListTitleView(titleName = "맥북", titleImg = R.drawable.img_notebook)
            LazyRow (
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
            ) {
                items(itemList) {
                    HomeItem(
                        onItemClicked = {},
                        onLikeClicked = {}
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.sdp)
//                    .padding(horizontal = 10.sdp)
//                    .background(BackgroundColor)
            )
        }
    }
}