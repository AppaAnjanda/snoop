package com.appa.snoop.presentation.ui.like.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.presentation.ui.like.Item
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LikeItem(item: Item, value: Boolean, onCheckedChange: (Any) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp, top = 12.sdp, bottom = 12.sdp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.sdp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Checkbox(
                checked = value,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.size(16.sdp),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Gray
                )
            )
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "삭제",
                modifier = Modifier.noRippleClickable { onClick() })

        }
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(80.sdp)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.img)
                    .build(),
                contentDescription = "제품 이미지",
                contentScale = ContentScale.FillWidth
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 4.sdp, end = 4.sdp, bottom = 16.sdp)
            ) {
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = item.name,
                    style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = "${PriceUtil.formatPrice(item.currentPrice.toString())}원",
                    style = TextStyle(fontSize = 14.ssp, fontWeight = FontWeight.Medium),
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "현재 지정 가격",
                modifier = Modifier.weight(3.5f),
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )

            Row(
                modifier = Modifier.weight(2.3f)
            ) {
                var price by remember {
                    mutableStateOf(item.goalPrice.toString())
                }
                PriceTextField(Modifier.weight(1f), price) {
                    price = it
                }
                Text(text = "원", style = TextStyle(fontSize = 14.ssp))
            }
        }

    }
}
