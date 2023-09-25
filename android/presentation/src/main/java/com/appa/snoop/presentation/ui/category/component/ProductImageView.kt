package com.appa.snoop.presentation.ui.category.component

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.LottieAnim
import com.appa.snoop.presentation.ui.category.utils.convertNaverUrl
import com.appa.snoop.presentation.ui.home.dumy.imageLinksToCoupang
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.GreenColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "[김희웅] ProductImageView"
@Composable
fun ProductImageView(
    modifier: Modifier = Modifier,
    product: Product,
    onLikeClicked: () -> Unit
) {
//    var isChecked by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(product.wishYn) }
    val context = LocalContext.current

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.sdp))
    ) {
        AsyncImage(
            modifier = modifier
                .width(140.sdp)
                .aspectRatio(1f)
                .align(Alignment.Center)
                .background(color = WhiteColor),
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.productImage)
                .build(),
            contentDescription = "제품 이미지",
            contentScale = ContentScale.FillWidth
        )

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
                    Log.d(TAG, "ProductImageView: ${url}")
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
        Box(
            modifier = modifier
                .padding(bottom = 8.sdp, end = 8.sdp)
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = modifier
                    .size(24.sdp)
                    .shadow(
                        elevation = 4.sdp,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(color = WhiteColor)
                    .padding(3.sdp)
                    .noRippleClickable {
                        onLikeClicked()
//                        isChecked = !isChecked
                    }
            ) {
                LottieAnim(
                    res = R.raw.lottie_like,
                    isChecked = isChecked,
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
//@Composable
//@Preview
//fun Preview() {
//    ProductImageView(
//        productState = "지금 최저가"
//    ) {}
//}