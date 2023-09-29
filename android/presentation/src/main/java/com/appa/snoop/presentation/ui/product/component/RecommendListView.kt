package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.common.product.HomeLabel
import com.appa.snoop.presentation.common.product.ProductItemView
import com.appa.snoop.presentation.ui.theme.BlackColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RecommendListView(
    modifier: Modifier = Modifier,
    products: List<Product>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "이런 제품은 어때요?",
            style = TextStyle(
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold,
                color = BlackColor,
            )
        )
        Spacer(modifier = modifier.height(16.sdp))
        LazyRow {
            items(products) { product ->
                ProductItemView(
                    modifier = Modifier,
                    product,
                    label = HomeLabel,
                    ratio = 0.85f,
                    onItemClicked = { /*TODO*/ }) {
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecommendListView() {
//    RecommendListView(modifier = Modifier, recommendProduct = Product())
}