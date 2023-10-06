package com.appa.snoop.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    product: Product,
    onItemClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(color = WhiteColor)
            .padding(horizontal = 16.sdp, vertical = 8.sdp)
            .noRippleClickable {
                onItemClicked()
            }
    ) {
        HomeItemImageView(
            product = product
        )
        Spacer(modifier = modifier.height(8.sdp))
        Text(
            modifier = modifier
                .width(140.sdp),
            text = product.productName,
            style = TextStyle(
                fontSize = 11.ssp,
                fontWeight = FontWeight.Bold,
                color = BlackColor,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = modifier.height(8.sdp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
        Text(
            text = PriceUtil.formatPrice(product.price.toString()) + " 원",
            style = TextStyle(
                fontSize = 16.ssp,
                fontWeight = FontWeight.ExtraBold,
            ),
        )
        Spacer(modifier = modifier.height(8.sdp))
    }
}