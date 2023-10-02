package com.appa.snoop.presentation.ui.mypage.component

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
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.product.HomeLabel
import com.appa.snoop.presentation.common.product.ProductItemView
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.mypage.common.MyPageLabel
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun MyPageInformation(member: Member) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(member.profileUrl)
//                .build(),
            model = member.profileUrl,
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(64.sdp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.size(16.sdp))
        Column {
            Text(
                text = member.nickname,
                style = TextStyle(fontSize = 16.ssp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.size(4.sdp))
            Text(
                text = member.email,
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
fun CurrentProductItemView(products : List<Product>, onItemClicked: (String) -> Unit) {
    Text(
        text = "최근 본 상품", style = TextStyle(
            fontSize = 14.ssp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
    )
    Spacer(modifier = Modifier.size(4.sdp))
    LazyRow {
        items(products) { product ->
            ProductItemView(
                product = product,
                label = HomeLabel,
                ratio = 0.85f,
                onItemClicked = {
                    onItemClicked(it)
                }) {
            }
        }
    }
}

@Composable
fun SettingComponent(index: Int, title: MyPageLabel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
            .noRippleClickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title.label, style = TextStyle(
                fontSize = 12.ssp,
                fontWeight = FontWeight.Normal
            )
        )
        DisplayIcon(title = title)
    }
}

@Composable
fun DisplayIcon(title: MyPageLabel) {
    when (title) {
        MyPageLabel.NOTIFICATION, MyPageLabel.MODIFY_PROFILE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "화면 이동",
                modifier = Modifier.size(12.sdp),
                tint = Color.Gray
            )
        }

        else -> {}
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MyPageItemViewPrivew() {
//    SettingComponent()
}