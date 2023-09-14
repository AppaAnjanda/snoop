package com.appa.snoop.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appa.snoop.presentation.ui.login.component.KakaoLoginButton
import com.appa.snoop.presentation.ui.login.component.LoginImage
import com.appa.snoop.presentation.ui.login.component.LoginText
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.util.effects.LoginLaunchEffect
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LoginScreen(
    navController: NavController
) {
    LoginLaunchEffect(navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp),
    ) {
        LoginText()
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            LoginImage()
        }
        KakaoLoginButton()
    }
}
