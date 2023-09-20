package com.appa.snoop.presentation.ui.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.ui.notification.component.NotificationItem
import com.appa.snoop.presentation.ui.notification.viewmodel.NotificationViewModel
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.NotificationLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationScreen(navController: NavController) {
    NotificationLaunchedEffect(navController = navController)
    val pages = listOf("찜 알림", "채팅 알림")
    val notificaitonViewModel = NotificationViewModel()
    val notificationState = notificaitonViewModel.notificationState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
    ) {
        val pagerState = rememberPagerState {
            pages.size
        }
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.sdp),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    width = 80.sdp
                )
            }) {
            pages.forEachIndexed { index, title ->
                Tab(
                    content = {
                        Spacer(modifier = Modifier.size(8.sdp))
                        Text(
                            text = title,
                            style = TextStyle(fontSize = 14.ssp, fontWeight = FontWeight.Normal)
                        )
                        Spacer(modifier = Modifier.size(8.sdp))
                    },
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )

            }
        }

        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = pages.size,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fill
        ) { page ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(
                    items = notificationState.value,
                    // Provide a unique key based on the email content
                    key = { _, item -> item.hashCode() }
                ) { _, content ->
                    // Display each email item
                    NotificationItem(content, onRemove = { currentItem ->
                        notificaitonViewModel.removeItem(currentItem)
                    })
                }
            }
        }
    }

}

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}