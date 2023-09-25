package com.appa.snoop.presentation.ui.mypage.modifyprofile

import android.net.Uri
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
import com.appa.snoop.presentation.util.Converter
import com.appa.snoop.presentation.util.effects.ModifyProfileLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ModifyProfileScreen(
    navController: NavController,
    showSnackBar: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: ModifyProfileViewModel = hiltViewModel()
) {
    ModifyProfileLaunchedEffect(navController)
    val focusManager = LocalFocusManager.current
    val scrollableState = rememberScrollState()
    val context = LocalContext.current
    val memberInfoState by mainViewModel.memberInfo.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nickName by remember { mutableStateOf(memberInfoState.nickname) }

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
            ModifyProfileImg(imageUri = imageUri, onImageChange = { uri -> imageUri = uri })
            Spacer(modifier = Modifier.size(16.sdp))
            ModifyProfilNickname(focusManager, memberInfoState.nickname) { value ->
                nickName = value
            }
        }

        Button(
            onClick = {
                val img = imageUri?.let { Converter.getRealPathFromUriOrNull(context, it) }
                if (img != null) {
                    viewModel.changeImage(img)
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