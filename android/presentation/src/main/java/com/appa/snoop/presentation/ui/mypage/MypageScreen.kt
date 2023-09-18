package com.appa.snoop.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.navigation.ModifyProfileNav
import com.appa.snoop.presentation.ui.mypage.common.MyPageLabel
import com.appa.snoop.presentation.ui.mypage.component.BottomSheet
import com.appa.snoop.presentation.ui.mypage.component.CurrentProductItemView
import com.appa.snoop.presentation.ui.mypage.component.MyPageInformation
import com.appa.snoop.presentation.ui.mypage.component.SettingComponent
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypageScreen(
    navController: NavController,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    if (sheetState.isVisible) {
        BottomSheet(sheetState) {
            scope.launch {
                sheetState.hide()
            }
        }
    }

    val scrollableState = rememberScrollState()
    val settings = listOf(
        MyPageLabel.NOTIFICATION,
        MyPageLabel.MODIFY_PROFILE,
        MyPageLabel.SELECT_CARD,
        MyPageLabel.LOGOUT,
        MyPageLabel.WITHDRAWAL,
    )
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
                    MyPageLabel.MODIFY_PROFILE -> navController.navigate(ModifyProfileNav.route)
                    MyPageLabel.SELECT_CARD ->  scope.launch {
                        sheetState.expand()
                    }
                    else -> {}
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