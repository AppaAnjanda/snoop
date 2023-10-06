package com.appa.snoop.presentation.ui.login.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.LightGreyColor
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GoSignupText(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "아직 회원이 아니신가요?",
        color = LightGreyColor,
        textDecoration = TextDecoration.Underline,
        fontSize = 10.ssp,
        modifier = modifier
            .clickable {
                onClick()
            }
    )
}