package com.appa.snoop.presentation.ui.signup

import android.view.Window
import android.view.WindowId
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.ui.signup.component.KakaoCertButton
import com.appa.snoop.presentation.ui.signup.component.PasswordTextField
import com.appa.snoop.presentation.ui.signup.component.SignupDoneButton
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.KakaoColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SignupScreen(
    navController: NavController
) {
    // TopBar 표기 해주기 위함
    val appBarState = rememberAppBarState(navController = navController)
    // text input focus 조절하기 위함
    val focusManager = LocalFocusManager.current

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 0.sdp, bottom = 16.sdp, start = 16.sdp, end = 16.sdp)
                .fillMaxSize()
                // 키보드 올라오면 뷰 같이 올리기 위함
                .imePadding(),
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
                            textId = it
                        },
                        focusManager = focusManager
                    )
                    Spacer(modifier = Modifier.width(10.sdp))

                    // TODO 카카오 인증 로직 구현
                    KakaoCertButton()
                }
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호",
                    text = textPass,
                    onValueChange = {
                        textPass = it
                    },
                    focusManager = focusManager
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호 확인",
                    text = textPassCheck,
                    onValueChange = {
                        textPassCheck = it
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
                    },
                    focusManager = focusManager
                )
            }
            SignupDoneButton()
        }
    }
}