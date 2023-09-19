package com.appa.snoop.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.login.component.GoSignupText
import com.appa.snoop.presentation.ui.login.component.LoginButton
import com.appa.snoop.presentation.ui.login.component.LoginIdTextField
import com.appa.snoop.presentation.ui.login.component.LoginImage
import com.appa.snoop.presentation.ui.login.component.LoginPasswordTextField
import com.appa.snoop.presentation.ui.login.component.LoginText
import com.appa.snoop.presentation.ui.signup.component.SignupPasswordTextField
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.LoginLaunchEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit
) {
    LoginLaunchEffect(navController)

    LaunchedEffect(
        loginViewModel.isLoginSuccessState
    ) {
        if (loginViewModel.isLoginSuccessState) {
            showSnackBar("로그인에 성공했습니다!")
            navController.popBackStack()
        }
    }

    val scrollableState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
    ) { paddingValues ->
        paddingValues

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.sdp, end = 16.sdp, bottom = 16.sdp)
                .verticalScroll(scrollableState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LoginText()
                LoginImage()
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LoginIdTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "아이디",
                    text = loginViewModel.textIdState,
                    onValueChange = {
                        loginViewModel.setTextId(it)
                    },
                    focusManager = focusManager,
                    loginViewModel = loginViewModel
                )
                LoginPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.sdp),
                    title = "비밀번호",
                    text = loginViewModel.textPasswordState,
                    onValueChange = {
                        loginViewModel.setTextPassword(it)
                    },
                    focusManager = focusManager
                )
                LoginButton(
                    loginViewModel = loginViewModel
                )
                Spacer(modifier = Modifier.size(6.sdp))
                GoSignupText(
                    onClick = {
                        navController.navigate(Router.LOGIN_SIGNUP_ROUTER_NAME)
                    }
                )
            }
        }
    }
}
