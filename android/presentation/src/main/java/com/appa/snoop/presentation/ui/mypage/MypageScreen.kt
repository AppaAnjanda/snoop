package com.appa.snoop.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.navigation.ModifyProfileNav
import com.appa.snoop.presentation.ui.home.component.HomeItem
import com.appa.snoop.presentation.ui.home.dumy.itemList
import com.appa.snoop.presentation.ui.mypage.component.CurrentProductItemView
import com.appa.snoop.presentation.ui.mypage.component.MyPageInformation
import com.appa.snoop.presentation.ui.mypage.component.SettingComponent
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun MypageScreen(
    navController: NavController,
) {
    MainLaunchedEffect(navController)
    val scrollableState = rememberScrollState()
    val settings = listOf("알림 설정", "프로필 변경", "로그아웃", "회원 탈퇴")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
    ) {
        MyPageInformation(User())
        Spacer(modifier = Modifier.size(16.sdp))
        CurrentProductItemView()
        Spacer(modifier = Modifier.size(8.sdp))
        HorizontalDivider(thickness = 6.sdp, color = BackgroundColor2)
        settings.forEachIndexed { index, title ->
            SettingComponent(index, title) {
                when (title) {
                    "프로필 변경" -> navController.navigate(ModifyProfileNav.route)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MypageScreenPreview() {
    MypageScreen(navController = rememberNavController())
}

data class User(
    val name: String = "기웃기웃",
    val email: String = "giusgius@gmail.com",
    val img: String = "https://picsum.photos/200"
)