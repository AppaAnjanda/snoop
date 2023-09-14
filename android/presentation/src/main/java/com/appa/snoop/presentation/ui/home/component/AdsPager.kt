package com.appa.snoop.presentation.ui.home.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.presentation.ui.theme.Black_70
import com.appa.snoop.presentation.ui.theme.NanumSquareNeo
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultipleImageView(
    modifier: Modifier = Modifier,
//    images: List<Image>
    imageLinks: List<String>,
    imageLinksToCoupang: List<String>
) {
    val pageCount = imageLinks.size
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
                        pagerState.animateScrollToPage((pagerState.currentPage + 1) % imageLinks.size)
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
            ImageViewWithNumber(number = page, imageLinks = imageLinks, imageLinksToCoupang = imageLinksToCoupang)
        }
        // 업로드 된 이미지 번호
        if (1 < imageLinks.size) {
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
                        text = "${pagerState.currentPage + 1} / ${imageLinks.size}",
                        style = TextStyle(
                            fontSize = 10.ssp,
                            fontWeight = FontWeight.Bold,
                            color = White,
                            textAlign = TextAlign.Center,
                        ),
                        fontFamily = NanumSquareNeo
                    )
                }
            }
        }
    }
}

@Composable
fun ImageViewWithNumber(
    modifier: Modifier = Modifier,
    number: Int,
//    images: List<Image>
    imageLinks: List<String>,
    imageLinksToCoupang: List<String>
) {
    val context = LocalContext.current
    AsyncImage(
//        model = imageLinks[number],
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageLinks[number])
            .build(),
        contentDescription = "업로드 사진",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                val url = imageLinksToCoupang[number] // 열고자 하는 링크 URL을 지정합니다.
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
    )
}