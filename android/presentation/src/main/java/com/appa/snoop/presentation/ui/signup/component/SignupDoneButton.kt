package com.appa.snoop.presentation.ui.signup.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.ui.signup.SignupViewModel
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.KakaoColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun SignupDoneButton(
    isIdValid: Boolean = false,
    isPasswordValid: Boolean = false,
    isNicknameValid: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .height(44.sdp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.sdp),
        elevation = ButtonDefaults.buttonElevation(2.sdp),
        enabled = (isIdValid && isPasswordValid && isNicknameValid),
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "회원가입 완료!",
                fontSize = 14.ssp,
                color = BlackColor
            )
        }
    }
}