package com.appa.snoop.presentation.ui.mypage.modifyprofile

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appa.snoop.domain.model.member.Nickname
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.mypage.modifyprofile.component.ModifyProfilNickname
import com.appa.snoop.presentation.ui.mypage.modifyprofile.component.ModifyProfileImg
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PermissionUtils
import com.appa.snoop.presentation.util.effects.ModifyProfileLaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "[김진영] ModifyProfileScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModifyProfileScreen(
    navController: NavController,
    showSnackBar: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: ModifyProfileViewModel = hiltViewModel()
) {
    ModifyProfileLaunchedEffect(navController)
    val galleryPermission =
        rememberMultiplePermissionsState(permissions = PermissionUtils.GALLERY_PERMISSIONS)

    val focusManager = LocalFocusManager.current
    val scrollableState = rememberScrollState()
    val context = LocalContext.current
    val memberInfoState by mainViewModel.memberInfo.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nickName by remember { mutableStateOf(memberInfoState.nickname) }
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    LaunchedEffect(Unit) {
        if (!galleryPermission.allPermissionsGranted) {
            galleryPermission.launchMultiplePermissionRequest()
        }
    }
    LaunchedEffect(key1 = memberInfoState) {
        mainViewModel.getMemberInfo()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(WhiteColor)
            .padding(start = 20.sdp, end = 20.sdp, top = 40.sdp, bottom = 16.sdp)
            .imePadding()
            .verticalScroll(scrollableState),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ModifyProfileImg(
                img = memberInfoState.profileUrl,
                imageUri = imageUri,
                granted = galleryPermission.allPermissionsGranted,
                /* TODO 권한 설정 화면 */
                showGranted = {
                    showSnackBar("권한 설정이 필요합니다!")
                    if (!galleryPermission.shouldShowRationale && !galleryPermission.allPermissionsGranted) {
                        context.startActivity(intent)
                    } else if (galleryPermission.shouldShowRationale) { // 한 번 거절
                        galleryPermission.launchMultiplePermissionRequest()
                    }
                },
                onImageChange = { uri ->
                    imageUri = uri
                    Log.d(TAG, "ModifyProfileScreen: $imageUri")
                })
            Spacer(modifier = Modifier.size(16.sdp))
            ModifyProfilNickname(focusManager, memberInfoState.nickname) { value ->
                nickName = value
            }
        }

        Button(
            onClick = {
                if (imageUri != null) {
                    Log.d(TAG, "ModifyProfileScreen: $imageUri")
                    viewModel.changeImage(context, imageUri!!)
                }
                viewModel.changeNickname(Nickname(nickName))
                showSnackBar("프로필이 변경되었습니다!")
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.sdp)
                .padding(4.sdp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.sdp),
            colors = ButtonDefaults.buttonColors(PrimaryColor),
            elevation = ButtonDefaults.buttonElevation(2.sdp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "변경사항 저장",
                    fontSize = 14.ssp
                )
            }
        }
    }

}