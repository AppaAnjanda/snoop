package com.appa.snoop.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.ui.signup.component.KakaoCertButton
import com.appa.snoop.presentation.ui.signup.component.SignupDoneButton
import com.appa.snoop.presentation.ui.signup.component.SignupPasswordTextField
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SignupScreen(
    navController: NavController
) {
    // TopBar 표기 해주기 위함
    val appBarState = rememberAppBarState(navController = navController)
    // text input focus 조절하기 위함
    val focusManager = LocalFocusManager.current
    val scrollableState = rememberScrollState()

    SignupLaunchedEffect(navController = navController)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
            .addFocusCleaner(focusManager)
//        topBar = { SharedTopAppBar(appBarState = appBarState) }
    ) { paddingValue ->
        paddingValue
        var textId by remember { mutableStateOf("") }
        var textPass by remember { mutableStateOf("") }
        var textPassCheck by remember { mutableStateOf("") }
        var textNickname by remember { mutableStateOf("") }

        var idValid by remember { mutableStateOf(false) }
        var passwordValid by remember { mutableStateOf(false) }
        var nicknameValid by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 0.sdp, bottom = 16.sdp, start = 16.sdp, end = 16.sdp)
                .fillMaxSize()
                // 키보드 올라오면 뷰 같이 올리기 위함
                .imePadding()
                .verticalScroll(scrollableState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // TODO 카카오 이메일 인증하면 아이디 입력하게 할지 카카오 이메일 쓸지 결정 해야됨.
                    SignupTextField(
                        modifier = Modifier
                            .weight(1f),
                        title = "이메일",
                        text = textId,
                        onValueChange = {
                            // TODO 서버 통신 코드 구현 필요
                            textId = it
                            idValid = textId.isNotEmpty()
                        },
                        focusManager = focusManager
                    )
                    Spacer(modifier = Modifier.width(10.sdp))

                    // TODO 카카오 인증 로직 구현
                    KakaoCertButton()
                }
                SignupPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호",
                    text = textPass,
                    onValueChange = { text, isValid ->
                        // TODO 서버 통신 코드 구현 필요
                        textPass = text
                        passwordValid = (textPass == textPassCheck && textPass.isNotEmpty() && isValid)
                    },
                    focusManager = focusManager
                )
                SignupPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호 확인",
                    text = textPassCheck,
                    onValueChange = { text, isValid ->
                        textPassCheck = text
                        passwordValid = (textPass == textPassCheck && textPassCheck.isNotEmpty() && isValid)
                    },
                    focusManager = focusManager
                )
                SignupTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "닉네임",
                    text = textNickname,
                    onValueChange = {
                        textNickname = it
                        nicknameValid = textNickname.isNotEmpty()
                    },
                    focusManager = focusManager
                )
            }
            SignupDoneButton(idValid, passwordValid, nicknameValid)
        }
    }
}