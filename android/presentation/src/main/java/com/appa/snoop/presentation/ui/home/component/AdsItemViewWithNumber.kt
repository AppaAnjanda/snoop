package com.appa.snoop.presentation.ui.home.component

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor_30
import com.appa.snoop.presentation.ui.theme.PrimaryColor_40
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AdsItemViewWithNumber(
    modifier: Modifier = Modifier,
    number: Int,
    productList: List<Product>
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryColor_40)
            .padding(horizontal = 12.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = modifier
                .clip(RoundedCornerShape(10.sdp))
                .width(110.sdp)
                .aspectRatio(1f)
//                .align(Alignment.Center)
                .background(color = WhiteColor),
            model = ImageRequest.Builder(LocalContext.current)
                .data(productList[number].productImage)
                .build(),
            contentDescription = "제품 이미지",
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(id = R.drawable.img_logo)
        )
        Spacer(modifier = Modifier.width(10.sdp))
        Column(
            modifier = Modifier
                .padding(vertical = 16.sdp)
        ) {
            Text(
                text = "지금 이 가격에 추천!",
                fontSize = 16.ssp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.sdp))
            Text(
                text = productList[number].productName,
                fontSize = 12.ssp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = PriceUtil.formatPrice(productList[number].price.toString()) + " 원",
                fontSize = 12.ssp,
                fontWeight = FontWeight.Bold,
                color = BlueColor
            )
        }
//        ClickableButton(
//            onClick = {
//
//            },
//            elevation = ButtonDefaults.buttonElevation(2.sdp),
//        ) {
//            Text(text = "찜 하기")
//        }
    }
}

@Preview
@Composable
fun AdsPreview() {
//    AdsItemViewWithNumber()
}