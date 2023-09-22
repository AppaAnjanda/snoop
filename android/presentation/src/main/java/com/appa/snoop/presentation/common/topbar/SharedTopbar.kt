package com.appa.snoop.presentation.common.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.utils.ActionsMenu
import com.appa.snoop.presentation.common.topbar.utils.AppBarState
import com.appa.snoop.presentation.ui.theme.GmarketSans
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopAppBar(
    appBarState: AppBarState,
    modifier: Modifier = Modifier,
//    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
//    val notificationList = notificationViewModel.notificationList.collectAsStateWithLifecycle()
//    Log.d("TEST", "SharedTopAppBar: ${notificationList.value.size}")

    // 센터 탑바라면
    if (appBarState.isCenterTopBar) {
        CenterAlignedTopAppBar(
            modifier = modifier
                .background(color = White),
            navigationIcon = {
                val icon = appBarState.navigationIcon
                val callback = appBarState.onNavigationIconClick
                if (icon != null) {
                    IconButton(onClick = { callback?.invoke() }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = appBarState.navigationIconContentDescription,
                            modifier = Modifier.size(14.sdp)
                        )
                    }
                }
            },
            title = {
                val title = appBarState.title
                if (title is String) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(fontFamily = GmarketSans, fontSize = 16.ssp)
                    )
                } else if (title is Int) {
                    Image(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(88.sdp),
                        painter = painterResource(id = R.drawable.img_logo), // 이미지 리소스
                        contentDescription = null, // 이미지 설명 (접근성을 위해 필요)
                    )
                }
            },
            actions = {
                val items = appBarState.actions
                if (items.isNotEmpty()) {
                    ActionsMenu(
                        items = items,
//                        notificationList.value.size
                    )
                }
            },
        )
    } else {
        TopAppBar(
            modifier = modifier
                .background(WhiteColor),
            navigationIcon = {
                val icon = appBarState.navigationIcon
                val callback = appBarState.onNavigationIconClick
                if (icon != null) {
                    IconButton(onClick = { callback?.invoke() }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = appBarState.navigationIconContentDescription,
                            modifier = Modifier.size(14.sdp)
                        )
                    }
                }
            },
            title = {
                val title = appBarState.title
                if (title is String) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(fontFamily = GmarketSans, fontSize = 16.ssp)
                    )
                } else if (title is Int) {
                    Image(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(88.sdp),
                        painter = painterResource(id = R.drawable.img_logo), // 이미지 리소스
                        contentDescription = null, // 이미지 설명 (접근성을 위해 필요)
                    )
                }
            },
            actions = {
                val items = appBarState.actions
                if (items.isNotEmpty()) {
                    ActionsMenu(
                        items = items,
//                        notificationList.value.size
                    )
                }
            },
        )
    }
}