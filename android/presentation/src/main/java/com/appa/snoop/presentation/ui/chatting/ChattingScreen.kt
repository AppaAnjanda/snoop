package com.appa.snoop.presentation.ui.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.ui.chatting.component.BottomChatFieldView
import com.appa.snoop.presentation.ui.chatting.component.MyChatView
import com.appa.snoop.presentation.ui.chatting.component.OtherChatView
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp

// TODO(실 데이터로 나중에 ChatList 교체)
@Composable
fun ChattingScreen(
    modifier: Modifier = Modifier,
    chatList: List<TestChatUser>
) {
    var chatTextState by rememberSaveable { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomChatFieldView(
                chatText = chatTextState,
                onTextChanged = {
                    chatTextState = it
                },
                onSendClicked = {
                    chatTextState = ""
                    // TODO(채팅 서버로 보내기)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = WhiteColor)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.sdp),
            contentPadding = PaddingValues(16.sdp)
        ) {
            items(chatList) { chat ->
                if (chat.myChat) {
                    MyChatView(user = chat)
                } else {
                    OtherChatView(user = chat)
                }
            }
            // TODO(보내기 View)
        }
    }
}

@Preview
@Composable
fun PreviewChattingScreen() {
    ChattingScreen(chatList = chatList)
}