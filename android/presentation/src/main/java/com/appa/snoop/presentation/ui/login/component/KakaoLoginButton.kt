package com.appa.snoop.presentation.ui.login.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.KakaoColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun KakaoLoginButton(
    modifier : Modifier = Modifier
) {
    ClickableButton(
        onClick = { /* TODO 로그인 구현 */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp)
            .shadow(2.sdp, shape = RoundedCornerShape(10.sdp)),
        shape = RoundedCornerShape(10.sdp),
        buttonColor = KakaoColor
//        colors = ButtonDefaults.buttonColors(PrimaryColor)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kakao),
                contentDescription = null
            )
            Spacer(modifier.width(8.sdp))
            Text(
                text = "카카오톡 로그인",
                fontSize = 12.ssp,
                color = BlackColor
            )
        }
    }
}

@Preview
@Composable
fun KakaoButtonPreview() {
    KakaoLoginButton()
}