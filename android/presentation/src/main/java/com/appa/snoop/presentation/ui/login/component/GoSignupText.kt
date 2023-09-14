package com.appa.snoop.presentation.ui.login.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import com.appa.snoop.presentation.ui.theme.BackgroundColor

@Composable
fun GoSignupText() {
    Text(
        text = "아직 회원이 아니신가요?",
        color = BackgroundColor,
        textDecoration = TextDecoration.Underline
    )
}