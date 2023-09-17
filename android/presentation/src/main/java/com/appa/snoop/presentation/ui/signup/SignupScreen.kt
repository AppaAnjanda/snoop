package com.appa.snoop.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.ui.signup.component.PasswordTextField
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.KakaoColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavController
) {
    val appBarState = rememberAppBarState(navController = navController)

    SignupLaunchedEffect(navController = navController)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor),
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
                .padding(16.sdp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SignupTextField(
                    modifier = Modifier
                        .weight(1f),
                    title = "아이디",
                    text = textId,
                    onValueChange = {
                        textId = it
                    }
                )
                Spacer(modifier = Modifier.width(10.sdp))
                ClickableButton(
                    onClick = { /* TODO 로그인 구현 */ },
                    modifier = Modifier
                        .height(40.sdp)
                        .shadow(2.sdp, shape = RoundedCornerShape(10.sdp)),
                    shape = RoundedCornerShape(10.sdp),
                    buttonColor = KakaoColor
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kakao),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.sdp))
                        Text(
                            text = "인증",
                            fontSize = 12.ssp,
                            color = BlackColor
                        )
                    }
                }
            }
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.sdp),
                title = "비밀번호",
                text = textPass,
                onValueChange = {
                    textPass = it
                }
            )
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.sdp),
                title = "비밀번호 확인",
                text = textPassCheck,
                onValueChange = {
                    textPassCheck = it
                }
            )
            SignupTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.sdp),
                title = "닉네임",
                text = textNickname,
                onValueChange = {
                    textNickname = it
                }
            )
        }
    }
}