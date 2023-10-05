package com.appa.snoop.presentation.common.product

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.calculateSize
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ProductItemView(
    modifier: Modifier = Modifier,
    product: Product,
    label: Label,
    ratio: Float,
    onItemClicked: (String) -> Unit,
    onLikeClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(color = WhiteColor)
            .padding(
                horizontal = ratio.calculateSize(16).sdp,
                vertical = ratio.calculateSize(8).sdp
            )
    ) {
        ProductImageView(
            ratio = ratio,
            product = product,
            img = product.productImage,
            onItemClicked = { onItemClicked(product.code) }
        ) {
            onLikeClicked()
        }
        Spacer(modifier = modifier.height(ratio.calculateSize(8).sdp))
        Text(
            modifier = modifier
                .width(ratio.calculateSize(140).sdp),
            text = product.productName,
            style = TextStyle(
                fontSize = ratio.calculateSize(11).ssp,
                fontWeight = FontWeight.Bold,
                color = BlackColor
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = modifier.height(ratio.calculateSize(8).sdp))
        Text(
            text = PriceUtil.formatPrice(product.price.toString()) + " 원",
            style = TextStyle(
                fontSize = ratio.calculateSize(16).ssp,
                fontWeight = FontWeight.ExtraBold,
            )
        )
    }
}

@Preview
@Composable
fun PreviewProductItemView() {
//    ProductItemView(
//        product = product,
//        label = HomeLabel,
//        ratio = 0.9f,
//        onItemClicked = {  }
//    ) {
//
//    }
}