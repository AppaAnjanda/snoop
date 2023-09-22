package com.appa.snoop.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeItemListTitleView(
    modifier: Modifier = Modifier,
    titleName: String,
    titleImg: Int = R.drawable.img_pin
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.sdp)
//            .background(BackgroundColor)
            .padding(start = 16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = titleImg), // 벡터 이미지 리소스
            contentDescription = null, // 이미지 설명 (접근성을 위해 필요)
            modifier = Modifier
                .size(16.sdp), // 이미지 크기 설정
        )
        Text(
            modifier = Modifier
                .padding(start = 2.sdp),
            text = titleName,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.ssp,
        )
        Text(
            modifier = Modifier
                .padding(start = 2.sdp),
            text = "가격 기웃기웃",
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
        )
    }
}