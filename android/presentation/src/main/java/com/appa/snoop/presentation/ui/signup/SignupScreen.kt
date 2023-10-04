package com.appa.snoop.presentation.ui.signup

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.ui.signup.component.KakaoCertButton
import com.appa.snoop.presentation.ui.signup.component.SignupDoneButton
import com.appa.snoop.presentation.ui.signup.component.SignupPasswordTextField
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import com.kakao.sdk.friend.m.t
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.onSubscription

private const val TAG = "[김희웅] SignupScreen"
@Composable
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel,
    showSnackBar: (String) -> Unit
) {
    val context = LocalContext.current
    // TopBar 표기 해주기 위함
//    val appBarState = rememberAppBarState(navController = navController)
    // text input focus 조절하기 위함
    val focusManager = LocalFocusManager.current
    val scrollableState = rememberScrollState()

    SignupLaunchedEffect(navController = navController)

    Log.d(TAG, "회원가입 페이지 컴포지션 됨")

    LaunchedEffect(
        signupViewModel.isSignupSuccess
    ) {
        if (signupViewModel.isSignupSuccess) {
            showSnackBar("회원가입을 환영합니다!")
            navController.popBackStack()
        }
    }
//    if (signupViewModel.isSignupSuccess) {
//        showSnackBar("로그인에 성공했습니다!")
//        navController.popBackStack()
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
            .addFocusCleaner(focusManager)
//        topBar = { SharedTopAppBar(appBarState = appBarState) }
    ) { paddingValue ->
        paddingValue
        // TODO viewmodel로 넣어야됨
        var textId by remember { mutableStateOf("") }
        var textPass by remember { mutableStateOf("") }
        var textPassCheck by remember { mutableStateOf("") }
        var textNickname by remember { mutableStateOf("") }

        var idValid by remember { mutableStateOf(false) }
        var passwordValid by remember { mutableStateOf(false) }
        var nicknameValid by remember { mutableStateOf(false) }

        idValid = signupViewModel.isKakaoLoginSuccess

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
                    SignupTextField(
                        modifier = Modifier
                            .weight(1f),
                        title = if (!signupViewModel.isKakaoLoginSuccess) "인증이 필요합니다." else "인증 되었습니다!",
                        text = signupViewModel.kakaoEmail,
                        onValueChange = {
                            textId = it
                        },
                        focusManager = focusManager,
                        enabled = false,
                        isCerted = signupViewModel.isKakaoLoginSuccess
                    )
                    Spacer(modifier = Modifier.width(10.sdp))

                    KakaoCertButton(
                        context,
                        signupViewModel
                    )
                }
                SignupPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호 (영어, 숫자, 특수문자 포함 8 ~ 20자)",
                    text = textPass,
                    onValueChange = { text, isValid ->
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
                // 비밀번호 동일 체크
                if (textPass.isNotEmpty() && textPassCheck.isNotEmpty()) {
                    if (textPass == textPassCheck) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.sdp),
                            text = "비밀번호가 일치합니다!",
                            fontSize = 10.ssp,
                            color = BlueColor
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.sdp),
                            text = "비밀번호가 일치하지 않습니다.",
                            fontSize = 10.ssp,
                            color = RedColor
                        )
                    }
                }
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
            SignupDoneButton(
                idValid,
                passwordValid,
                nicknameValid,
                onClick = {
                    signupViewModel.signUp(
                        email = signupViewModel.kakaoEmail,
                        password = textPass,
                        nickname = textNickname
                    )
                }
            )
        }
    }
}