package com.appa.snoop.presentation.ui.login.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.ui.theme.SnoopTheme
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LoginText(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = Modifier.height(10.sdp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "로그인이 필요한 기능입니다.",
            fontSize = 22.ssp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.height(10.sdp)
        )
        Text(
            text = "로그인 하면 기웃기웃만의 다양한 기능을\n활용할 수 있습니다.",
            fontSize = 14.ssp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
@Preview
fun LoginTextPreview() {
    LoginText()
}