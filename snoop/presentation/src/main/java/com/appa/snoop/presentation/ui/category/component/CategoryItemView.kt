package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit,
    onLikeClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(color = WhiteColor)
            .padding(horizontal = 16.sdp, vertical = 8.sdp)
    ) {
        ProductImageView(
            productState = "최저가"
        ) {
            onLikeClicked()
        }
        Spacer(modifier = modifier.height(8.sdp))
        Text(
            modifier = modifier
                .width(140.sdp),
            text = "Apple 맥북 프로 14 스페이스 그레이 M2 pro 10코어",
            style = TextStyle(
                fontSize = 11.ssp,
                fontWeight = FontWeight.Bold,
                color = BlackColor,
            )
        )
        Spacer(modifier = modifier.height(8.sdp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "2,790,000원",
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = DarkGrayColor,
                    textDecoration = TextDecoration.LineThrough,
                )
            )
            Spacer(modifier = modifier.width(4.sdp))
            Text(
                text = "10.0%",
                style = TextStyle(
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = RedColor,
                )
            )
            Image(
                painterResource(id = R.drawable.ic_increase),
                contentDescription = "가격 하락",
                modifier = modifier.size(12.sdp)
            )
        }
        Text(
            text = "2,620,000원",
            style = TextStyle(
                fontSize = 16.ssp,
                fontWeight = FontWeight.ExtraBold,
            )
        )
    }
}

@Preview
@Composable
fun PreviewCategoryItem() {
    CategoryItem(
        modifier = Modifier,
        onItemClicked = {},
        onLikeClicked = {}
    )
}