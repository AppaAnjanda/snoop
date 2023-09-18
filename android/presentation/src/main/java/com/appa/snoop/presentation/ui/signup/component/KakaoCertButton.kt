package com.appa.snoop.presentation.ui.signup.component

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.appa.snoop.presentation.ui.signup.SignupViewModel
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.KakaoColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "KakaoCertButton_싸피"
@Composable
fun KakaoCertButton(
    context: Context,
    signupViewModel: SignupViewModel,
) {
    ClickableButton(
        onClick = { signupViewModel.kakaoLogin(context, signupViewModel.kakaoCallback) },
        modifier = Modifier
            .height(36.sdp),
        shape = RoundedCornerShape(10.sdp),
        buttonColor = KakaoColor,
        elevation = ButtonDefaults.buttonElevation(2.sdp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kakao),
                contentDescription = null,
                modifier = Modifier
                    .width(12.sdp)
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