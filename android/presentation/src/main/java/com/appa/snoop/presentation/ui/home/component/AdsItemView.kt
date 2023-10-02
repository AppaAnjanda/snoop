package com.appa.snoop.presentation.ui.home.component

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.presentation.ui.category.utils.convertNaverUrl
import com.appa.snoop.presentation.ui.theme.Black_70
import com.appa.snoop.presentation.ui.theme.NanumSquareNeo
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdsItem(
    modifier: Modifier = Modifier,
    productList: List<Product>
) {
    val context = LocalContext.current
    val pageCount = productList.size
    val pagerState = rememberPagerState(initialPage = 0) { pageCount }

    // 지정한 시간마다 auto scroll.
    // 유저의 스크롤해서 페이지가 바뀐경우 다시 실행시키고 싶기 때문에 key는 currentPage.
    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            while (true) {
                delay(3_000)
                // 페이지 바뀌었다고 애니메이션이 멈추면 어색하니 NonCancellable
                withContext(NonCancellable) {
                    // 일어날린 없지만 유저가 약 10억번 스크롤할지 몰라.. 하는 사람을 위해..
                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
                        // 페이지 사이즈 넘어가면 다시 1페이지로
                        pagerState.animateScrollToPage((pagerState.currentPage + 1) % productList.size)
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2.2f)
            .clip(RoundedCornerShape(10.sdp)),
    ) {
        HorizontalPager(
            beyondBoundsPageCount = pageCount,
            state = pagerState
        ) { page ->
            AdsItemViewWithNumber(
                modifier = Modifier
                    .noRippleClickable {
                        val url =
                            if (productList[page].provider == "네이버")
                                convertNaverUrl(
                                    productList[page].productLink
                                )
                            else
                                productList[page].productLink
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                number = page,
                productList = productList
            )
        }
        // 업로드 된 이미지 번호
        if (1 < productList.size) {
            Box(
                modifier = modifier
                    .padding(0.sdp)
                    .align(Alignment.BottomEnd)
            ) {
                Box(
                    modifier = modifier
                        .size(50.sdp, 28.sdp)
                        .clip(RoundedCornerShape(10.sdp))
                        .background(color = Black_70)
                        .align(Alignment.Center)
                ) {
                    Text(
                        modifier = modifier.align(Alignment.Center),
                        text = "${pagerState.currentPage + 1} / ${productList.size}",
                        style = TextStyle(
                            fontSize = 10.ssp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        ),
                        fontFamily = NanumSquareNeo
                    )
                }
            }
        }
    }
}