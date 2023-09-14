package com.appa.snoop.presentation.ui.notification.component

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.notification.viewmodel.NotificationViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationView() {
    val pages = listOf("찜 알림", "채팅 알림")
    val notificaitonViewModel = NotificationViewModel()
    val notificationState = notificaitonViewModel.notificationState
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
                val messages =
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(
    item: Notification,
    onRemove: (Notification) -> Unit
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150f }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                NotificationItemComponent(item)
            }
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(300)
            onRemove(currentItem)
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent // dismissThresholds 만족 안한 상태
            DismissValue.DismissedToEnd -> Color.Green.copy(alpha = 0.4f) // -> 방향 스와이프 (수정)
            DismissValue.DismissedToStart -> Color.LightGray.copy(alpha = 0.5f) // <- 방향 스와이프 (삭제)
        }, label = ""
    )
//    val color = when (dismissState.dismissDirection) {
//        DismissDirection.StartToEnd -> Color(0xFF1DE9B6)
//        DismissDirection.EndToStart -> Color(0xFFBBBBBB)
//        null -> Color.Transparent
//    }
//    val icon = when (dismissState.targetValue) {
//        DismissValue.Default -> painterResource(R.drawable.ic_round_circle_24)
//        DismissValue.DismissedToEnd -> painterResource(R.drawable.ic_round_edit_24)
//        DismissValue.DismissedToStart -> painterResource(R.drawable.ic_round_delete_24)
//    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Delete,
            contentDescription = "Archive",
            tint = Color.White
        )
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
    Column {
        Row(
            modifier = Modifier.padding(
                start = 16.sdp,
                end = 16.sdp,
                top = 10.sdp,
                bottom = 10.sdp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "로고",
                        modifier = Modifier.size(20.sdp)
                    )
                    Spacer(modifier = Modifier.size(4.sdp))
                    Text(
                        text = "${item.type} 알림",
                        style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.ssp)
                    )
                }
                Spacer(modifier = Modifier.size(4.sdp))
                Text(
                    text = "${item.name} 현재 ${item.type}입니다!",
                    lineHeight = 18.ssp,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.ssp,
                        color = Color.Gray
                    )
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.img)
                    .build(),
                contentDescription = "상품 사진",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Inside
            )
        }
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
    }


}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun NotificationViewPreview() {
    NotificationView()
}

data class Notification(
    var id: Int = 1,
    var type: String = "최저가",
    var name: String = "Apple 맥북 프로 14 스페이스 그레이 M2 pro 10코어",
    var img: String = "https://media.istockphoto.com/id/1358386001/photo/apple-macbook-pro.jpg?s=612x612&w=0&k=20&c=d14HA-i0EHpdvNvccdJQ5pAkQt8bahxjjb6fO6hs4E8=",
    var date: String = "2023-09-12"
)
