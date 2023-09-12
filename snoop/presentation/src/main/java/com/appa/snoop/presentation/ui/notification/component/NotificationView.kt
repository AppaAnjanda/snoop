package com.appa.snoop.presentation.ui.notification.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationView() {
    val notificationList = listOf(Notification(), Notification(), Notification())
    val pages = listOf("찜 알림", "채팅 알림")
    Column(modifier = Modifier.fillMaxSize()) {
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
            Text(text = pages[page], style = TextStyle(fontSize = 20.ssp))
        }
    }

}

@Composable
fun NotificationSettingComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "다양한 혜택 알림을 받으려면 알림을 켜주세요.", fontWeight = FontWeight.Normal, fontSize = 12.ssp)
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            contentDescription = "알림 설정 화면 이동",
            colorFilter = ColorFilter.tint(Color.Gray)
        )
    }
}

@Composable
fun NotificationItemComponent(item: Notification) {

}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun NotificationViewPreview() {
    NotificationView()
}

data class Notification(
    var id: Int = 1,
    var name: String = "최저가 알림",
    var img: String = "https://media.istockphoto.com/id/1358386001/photo/apple-macbook-pro.jpg?s=612x612&w=0&k=20&c=d14HA-i0EHpdvNvccdJQ5pAkQt8bahxjjb6fO6hs4E8=",
    var date: String = "2023-09-12"
)
