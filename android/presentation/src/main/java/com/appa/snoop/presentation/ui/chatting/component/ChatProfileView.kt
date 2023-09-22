package com.appa.snoop.presentation.ui.chatting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ChatProfileView(
    modifier: Modifier = Modifier,
    profileUrl: String
) {
    Box(
        modifier = modifier
            .size(40.sdp)
            .clip(shape = CircleShape)
            .background(color = WhiteColor)
            .border(
                width = 2.sdp,
                shape = CircleShape,
                color = BackgroundColor
            )
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center),
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileUrl)
                .build(),
            contentDescription = "프로필 이미지",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun PreviewChatProfileView() {
    ChatProfileView(profileUrl = "")
}