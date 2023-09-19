package com.appa.snoop.presentation.ui.chatting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.ui.chatting.TestChatUser
import com.appa.snoop.presentation.ui.chatting.dummyChat1
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor_70
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun MyChatView(
    modifier: Modifier = Modifier,
    user: TestChatUser
) {
    Column(
        modifier = Modifier
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.sdp)
    ) {
        Box(modifier = Modifier
            .clip(shape = RoundedCornerShape(10.sdp, 4.sdp, 10.sdp, 10.sdp))
            .background(color = PrimaryColor_70)
        ) {
            Box(
                modifier = modifier
                    .padding(8.sdp)
            ) {
                Text(
                    text = user.text,
                    style = TextStyle(
                        color = BlackColor,
                        fontSize = 12.ssp
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = user.time,
            style = TextStyle(
                color = BlackColor,
                fontSize = 10.ssp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyChattingViewPreview() {
    MyChatView(
        user = dummyChat1
    )
}