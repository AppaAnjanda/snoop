package com.appa.snoop.presentation.ui.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appa.snoop.presentation.ui.chatting.component.BottomChatFieldView
import com.appa.snoop.presentation.ui.chatting.component.MyChatView
import com.appa.snoop.presentation.ui.chatting.component.OtherChatView
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.ChattingLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import com.appa.snoop.presentation.util.rememberImeState
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch
import okhttp3.internal.notify

private const val TAG = "[김희] ChattingScreen_싸피"
// TODO(실 데이터로 나중에 ChatList 교체)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChattingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    roomNumber: Int,
    chattingViewModel: ChattingViewModel = hiltViewModel()
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val lazyState = rememberLazyListState()
    val chatList = chattingViewModel.chatList.value
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    //TODO 구현
    LaunchedEffect(Unit) {
        chattingViewModel.setRoomNumber(roomNumber)
        chattingViewModel.runStomp()
    }
//    chattingViewModel.setRoomNumber(4)
//    chattingViewModel.runStomp()

    LaunchedEffect(imeState.value, chattingViewModel.chatList.value, chattingViewModel.chatRecieveState) {
        Log.d(TAG, "ChattingScreen: 업데이트 되었습니다 ${chattingViewModel.chatList.value}")
//        scrollState.animateScrollTo(chatList.lastIndex, tween(300))
        lazyState.animateScrollToItem(0)
    }

    ChattingLaunchedEffect(
        navController = navController,
        chattingViewModel = chattingViewModel
    )
    var chatTextState by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .addFocusCleaner(focusManager)
            .imePadding(),
        bottomBar = {
            BottomChatFieldView(
                modifier = Modifier,
                chatText = chatTextState,
                focusManager = focusManager,
                onTextChanged = {
                    chatTextState = it
                },
                onSendClicked = {
                    scope.launch {
                        chattingViewModel.sendStomp(chatTextState)
//                    scope.launch {
//                        chattingViewModel.sendTest(chatTextState)
//                    }
//                    focusManager.clearFocus()
                        chatTextState = ""
                    }
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
            contentPadding = PaddingValues(16.sdp),
            state = lazyState,
            reverseLayout = true
        ) {
            items(
                chattingViewModel.chatList.value,
//                chattingViewModel.chatListState.value
            ) { chat ->
                //TODO 이메일 Shared에서 가져와서 변경
                if (chat.email == "skdi550@nate.com") {
                    MyChatView(user = chat)
                } else {
                    OtherChatView(user = chat)
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewChattingScreen() {
//    ChattingScreen(chatList = chatList)
//}