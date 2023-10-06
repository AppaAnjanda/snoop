package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ProductDetailView(
    modifier: Modifier = Modifier,
    product: Product,
    onSharedClicked: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.sdp))
        ) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = WhiteColor)
                    .aspectRatio(1f)
                    .align(Alignment.Center),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.productImage)
                    .build(),
                contentDescription = "제품 이미지",
                contentScale = ContentScale.FillWidth
            )
        }
        Spacer(modifier = modifier.height(12.sdp))
        Text(
            modifier = modifier.fillMaxWidth(),
            text = product.productName,
            style = TextStyle(
                fontSize = 16.ssp,
                fontWeight = FontWeight.Bold,
                color = BlackColor,
            )
        )
        Spacer(modifier = modifier.height(8.sdp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = PriceUtil.formatPrice(product.price.toString()) + " 원",
                style = TextStyle(
                    fontSize = 24.ssp,
                    fontWeight = FontWeight.ExtraBold,
                )
            )
            Box(
                modifier = modifier
                    .size(40.sdp)
                    .clip(CircleShape)
                    .border(1.dp, color = DarkGrayColor, shape = CircleShape)
                    .background(color = WhiteColor)
                    .noRippleClickable {
                        // TODO(링크 공유하기 기능)
                        onSharedClicked(product.productLink)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "공유",
                    tint = DarkGrayColor,
                    modifier = modifier.align(Alignment.Center)
                        .size(16.sdp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProductDetailView() {
//    ProductDetailView {}
}