package com.appa.snoop.presentation.ui.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.ui.login.LoginViewModel
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlin.math.log

@Composable
fun LoginButton(
//    idFilled: Boolean = false,
//    passwordFilled: Boolean = false,
    modifier : Modifier = Modifier,
    loginViewModel: LoginViewModel
) {
    ClickableButton(
        onClick = {
                  loginViewModel.login()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp),
        shape = RoundedCornerShape(10.sdp),
        buttonColor = PrimaryColor,
        elevation = ButtonDefaults.buttonElevation(2.sdp),
//        enabled = idFilled && passwordFilled
        enabled = loginViewModel.idFilledState && loginViewModel.passwordFilledState
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.img_logo),
//                contentDescription = null
//            )
//            Spacer(modifier.width(8.sdp))
            Text(
                text = "기웃기웃 로그인",
                fontSize = 14.ssp,
                color = BlackColor
            )
        }
    }
}