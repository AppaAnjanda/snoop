package com.appa.snoop.presentation.ui.chatting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.domain.model.chat.ChatItem
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.util.LinkifyText
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

const val baseImgUrl = "https://d3oh64wne7x39t.cloudfront.net/%E1%84%86%E1%85%B5%E1%84%8B%E1%85%A5%E1%84%8F%E1%85%A2%E1%86%BA.jpeg"
@Composable
fun OtherChatView(
    modifier: Modifier = Modifier,
    user: ChatItem
) {
    Row {
        ChatProfileView(
            profileUrl = user.imageUrl ?: baseImgUrl
        )
        Spacer(modifier = modifier.width(8.sdp))
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(top = 8.sdp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.sdp)
        ) {
            Text(
                text = user.sender,
                style = TextStyle(
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BlackColor,
                )
            )
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(4.sdp, 10.sdp, 10.sdp, 10.sdp))
                .background(color = BackgroundColor)
            ) {
                Box(
                    modifier = modifier
                        .padding(8.sdp)
                ) {
                    LinkifyText(
                        text = user.msg,
                        style = TextStyle(
                            color = BlackColor,
                            fontSize = 12.ssp
                        ),
                        fontWeight = FontWeight.Medium,
                        maxLines = 10
                    )
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.sdp),
//                    .fillMaxWidth(),
                text = user.time,
                style = TextStyle(
                    color = BlackColor,
                    fontSize = 10.ssp
                ),
//                textAlign = TextAlign.Right
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewOtherChatView() {
//    OtherChatView(
//        user = TestChatUser(
//            profileUrl = "",
//            name = "김희웅",
//            text = "테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅",
//            time = "오후 6:32"
//        )
//    )
//}