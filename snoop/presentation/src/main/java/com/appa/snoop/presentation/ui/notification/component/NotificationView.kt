package com.appa.snoop.presentation.ui.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun NotificationView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp)
    ) {
        item {
            NotificationSettingComponent()
        }
        items(3) {

        }
    }
}

@Composable
fun NotificationSettingComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "다양한 혜택 알림을 받으려면 알림을 켜주세요.", fontWeight = FontWeight.Normal, fontSize = 12.ssp)
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            contentDescription = "알림 설정 화면 이동",
            colorFilter = ColorFilter.tint(Color.Gray)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun NotificationViewPreview() {
    NotificationView()
}
