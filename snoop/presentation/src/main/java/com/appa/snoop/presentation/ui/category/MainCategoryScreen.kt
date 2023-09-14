package com.appa.snoop.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appa.snoop.presentation.ui.theme.YellowColor
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect

@Composable
fun MainCategoryScreen(
    navController: NavController
) {
    MainLaunchedEffect(navController)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "category",
            fontSize = 30.sp
        )
        // 테스트 코드
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(YellowColor)
        )
    }
}