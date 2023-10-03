package com.appa.snoop.presentation.ui.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appa.snoop.presentation.MainActivity
import com.appa.snoop.presentation.navigation.ModifyProfileNav
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.mypage.common.MyPageLabel
import com.appa.snoop.presentation.ui.mypage.component.BottomSheet
import com.appa.snoop.presentation.ui.mypage.component.CurrentProductItemView
import com.appa.snoop.presentation.ui.mypage.component.LogoutDialog
import com.appa.snoop.presentation.ui.mypage.component.MyPageInformation
import com.appa.snoop.presentation.ui.mypage.component.SettingComponent
import com.appa.snoop.presentation.ui.mypage.utils.findActivity
import com.appa.snoop.presentation.ui.mypage.utils.openSourceLicenses
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

private const val TAG = "[김진영] MypageScreen"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypageScreen(
    navController: NavController,
    showSnackBar: (String) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    MainLaunchedEffect(navController)
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showDialog by remember { mutableStateOf(false) }
    val scrollableState = rememberScrollState()
    val settings = listOf(
        MyPageLabel.NOTIFICATION,
        MyPageLabel.MODIFY_PROFILE,
        MyPageLabel.SELECT_CARD,
        MyPageLabel.LOGOUT,
        MyPageLabel.OSS,
        MyPageLabel.WITHDRAWAL,
    )
    val context = LocalContext.current
    val notificationManager = NotificationManagerCompat.from(context)
    val notificationPermissionGranted = notificationManager.areNotificationsEnabled()
    val openNotificationSettings =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    if (sheetState.isVisible) {
        BottomSheet(viewModel = myPageViewModel, sheetState) {
            scope.launch {
                sheetState.hide()
            }
        }
    }

    if (showDialog) {
        LogoutDialog(visible = showDialog,
            onConfirmRequest = {
                mainViewModel.logout()
                navController.popBackStack()
                showSnackBar("로그아웃하였습니다!")
            },
            onDismissRequest = {
                showDialog = !showDialog
            })
    }

    val memberInfoState by mainViewModel.memberInfo.collectAsState()
    val recentProduct by myPageViewModel.recentProductState.collectAsState()
    LaunchedEffect(key1 = Unit, key2 = memberInfoState) {
        Log.d(TAG, "MypageScreen 확인!!: $memberInfoState")
        mainViewModel.getMemberInfo()
        myPageViewModel.getRecentProduct()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
    ) {
        MyPageInformation(memberInfoState)
        Spacer(modifier = Modifier.size(16.sdp))
        CurrentProductItemView(products = recentProduct, onItemClicked = { code ->
            val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                "{productCode}",
                code
            )
            navController.navigate(route)
        })
        Spacer(modifier = Modifier.size(8.sdp))
        HorizontalDivider(thickness = 6.sdp, color = BackgroundColor2)
        settings.forEachIndexed { index, title ->
            SettingComponent(index, title) {
                when (title) {
                    MyPageLabel.NOTIFICATION -> {
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        openNotificationSettings.launch(intent)
                    }

                    MyPageLabel.MODIFY_PROFILE -> navController.navigate(ModifyProfileNav.route)
                    MyPageLabel.SELECT_CARD -> scope.launch {
                        sheetState.partialExpand()
                    }

                    MyPageLabel.LOGOUT -> showDialog = true
                    MyPageLabel.OSS -> openSourceLicenses(context)
                    else -> {}
                }
            }
        }
    }
}
