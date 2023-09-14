package com.appa.snoop.presentation.ui.main

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appa.snoop.presentation.navigation.MainNav
import com.appa.snoop.presentation.navigation.NavUtil
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.NavBarColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

// 바텀 네비 리플 색 없애기
private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

@Composable
fun CustomTabBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Category,
        MainNav.Like,
        MainNav.MyPage,
    )
    val indicatorColor by rememberUpdatedState(PrimaryColor) // 선택된 탭의 indicator 색상

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        TabRow(
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                    )
                    clip = true
                    shadowElevation = 20f
                },
            selectedTabIndex = bottomNavigationItems.indexOfFirst { it.route == currentRoute },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = indicatorColor,
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            tabPositions[bottomNavigationItems.indexOfFirst { it.route == currentRoute }],
                            40.sdp
                        )
                        .graphicsLayer {
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                            clip = true
                        }
                )
            },
            contentColor = DarkGrayColor, // 텍스트 색상
            containerColor = WhiteColor
        ) {
            bottomNavigationItems.forEach { item ->
                val selected = currentRoute == item.route
                Tab(
                    modifier = Modifier.size(60.sdp),
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            item.route,
                            modifier = Modifier
                                .size(16.sdp)
                        )
                    },
                    selected = selected,
                    onClick = {
                        NavUtil.navigate(
                            navController, item.route,
                            navController.graph.startDestinationRoute
                        )
                    },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 9.ssp
                        )
                    },
                    selectedContentColor = PrimaryColor,
                    unselectedContentColor = DarkGrayColor,
                )
            }
        }
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.TopStart) // 여기 수정
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}