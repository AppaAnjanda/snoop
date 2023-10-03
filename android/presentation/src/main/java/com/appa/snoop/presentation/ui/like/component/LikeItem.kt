package com.appa.snoop.presentation.ui.like.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.NoRippleInteractionSource
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LikeItem(
    item: WishBox,
    value: Boolean,
    focusManager: FocusManager,
    onCheckedChange: (Any) -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp, top = 12.sdp, bottom = 12.sdp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.sdp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "삭제",
                modifier = Modifier
                    .noRippleClickable { onDeleteClick() })

        }
        Row {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .size(76.sdp)
                        .aspectRatio(1f)
                        .padding(1.sdp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.productImage)
                        .build(),
                    contentDescription = "제품 이미지",
                    contentScale = ContentScale.FillWidth
                )
                Surface(
                    modifier = Modifier.size(16.sdp),
                    shape = ShapeDefaults.Small,
                    color = Color.White.copy(alpha = 0.7f)
                ) {}
                Checkbox(
                    checked = value,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.size(16.sdp),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Gray,
                        disabledUncheckedColor = Color.White,
                        disabledIndeterminateColor = Color.White,

                        ),
                    interactionSource = NoRippleInteractionSource()
                )
            }
            Spacer(modifier = Modifier.size(4.sdp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 4.sdp, end = 4.sdp, bottom = 16.sdp)
            ) {
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = item.productName,
                    style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = "${PriceUtil.formatPrice(item.price.toString())}원",
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
                modifier = Modifier.weight(4.0f),
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )

            Row(
                modifier = Modifier.weight(2.8f),
                horizontalArrangement = Arrangement.Center
            ) {
                var price by remember {
                    mutableStateOf(item.alertPrice.toString())
                }
                PriceTextField(
                    Modifier.weight(1f),
                    text = price,
                    focusManager = focusManager,
                    onPriceChanged = {
                        price = it
                    },
                    onUpdateClick = {
                        onUpdateClick(it)
                    })
            }
        }

    }
}
