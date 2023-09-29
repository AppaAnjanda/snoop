package com.appa.snoop.presentation.common.product

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.LottieAnim
import com.appa.snoop.presentation.ui.category.utils.convertNaverUrl
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.GreenColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.extensions.calculateSize
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ProductImageView(
    modifier: Modifier = Modifier,
    ratio: Float,
    product: Product,
    img: String,
    onItemClicked:() -> Unit,
    onLikeClicked: () -> Unit
) {
    val context = LocalContext.current
    var liked by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(ratio.calculateSize(10).sdp)).noRippleClickable {
                onItemClicked()
            }
    ) {
        AsyncImage(
            modifier = modifier
                .width(ratio.calculateSize(140).sdp)
                .aspectRatio(1f)
                .align(Alignment.Center),
            model = ImageRequest.Builder(LocalContext.current)
                .data(img)
                .build(),
            contentDescription = "제품 이미지",
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = modifier
                .padding(ratio.calculateSize(2).sdp)
        ) {
            Box(
                modifier = modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(10.sdp))
                    .background(
                        color = if (product.provider == "쿠팡") BlueColor else GreenColor
                    )
                    .clickable {
                        val url =
                            if (product.provider == "네이버")
                                convertNaverUrl(
                                    product.productLink
                                )
                            else
                                product.productLink
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
            ) {
                Text(
                    modifier = modifier
                        .padding(horizontal = 12.sdp, vertical = 4.sdp),
                    text = if (product.provider == "쿠팡") product.provider else "네이버",
                    style = TextStyle(
                        fontSize = 8.ssp,
                        fontWeight = FontWeight.ExtraBold,
                        color = WhiteColor,
                    )
                )
            }
        }
        Box(
            modifier = modifier
                .padding(
                    bottom = ratio.calculateSize(8).sdp,
                    end = ratio.calculateSize(8).sdp
                )
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = modifier
                    .size(ratio.calculateSize(24).sdp)
                    .shadow(
                        elevation = ratio.calculateSize(4).sdp,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(color = WhiteColor)
                    .noRippleClickable {
                        // TODO("서버에 찜목록 추가")
                        onLikeClicked()
                        liked = !liked
                    }
                    .padding(ratio.calculateSize(3).sdp)
            ) {
                LottieAnim(
                    modifier = modifier.align(Alignment.Center),
                    isChecked = isChecked,
                    res = R.raw.lottie_like,
                    startTime = 0.2f,
                    endTime = 0.7f,
                    onClick = {
                        isChecked = !isChecked
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
//    ProductImageView(
//        ratio = 0.7f,
//        img = "",
//        productState = "지금 최저가"
//    ) {}
}