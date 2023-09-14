package com.appa.snoop.presentation.ui.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.home.component.HomeItem
import com.appa.snoop.presentation.ui.home.dumy.itemList
import com.appa.snoop.presentation.ui.mypage.User
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun MyPageInformation(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.img, contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(64.sdp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(16.sdp))
        Column {
            Text(
                text = user.name,
                style = TextStyle(fontSize = 16.ssp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.size(4.sdp))
            Text(
                text = user.email,
                style = TextStyle(
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        }
    }
}

@Composable
fun CurrentProductItemView() {
    Text(
        text = "최근 본 상품", style = TextStyle(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
    )
    Spacer(modifier = Modifier.size(4.sdp))
    LazyRow {
        items(itemList) {
            HomeItem(
                onItemClicked = {},
                onLikeClicked = {}
            )
        }
    }
}

@Composable
fun SettingComponent(index: Int, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = TextStyle(
                fontSize = 12.ssp,
                fontWeight = FontWeight.Normal
            )
        )
        DisplayIcon(title = title)
    }
}

@Composable
fun DisplayIcon(title: String) {
    when (title) {
        "알림 설정", "프로필 변경" -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "화면 이동",
                modifier = Modifier.size(12.sdp),
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MyPageItemViewPrivew() {
//    SettingComponent()
}