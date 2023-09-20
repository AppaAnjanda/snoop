package com.appa.snoop.presentation.ui.like

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect

@Composable
fun LikeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    MainLaunchedEffect(navController)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "like",
            fontSize = 30.sp
        )
        Button(
            onClick = {
                mainViewModel.logout()
            }
        ) {
            Row(

            ) {
                Text(text = "로그아웃 테스트")
            }
        }

        // 테스트 코드
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor)
        )
    }
}