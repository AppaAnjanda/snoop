package com.appa.snoop.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.component.MainTopbar
import com.appa.snoop.presentation.common.topbar.component.SearchTopbar
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.util.effects.SearchLaunchedEffect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SearchScreen(
    navController: NavController
) {
    SearchLaunchedEffect(navController)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "mypage",
            fontSize = 30.sp
        )

        // 테스트 코드
        // 테스트 코드
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueColor)
        )
    }
}