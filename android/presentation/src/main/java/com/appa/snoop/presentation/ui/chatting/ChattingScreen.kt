package com.appa.snoop.presentation.ui.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.button.ClickableButton
import com.appa.snoop.presentation.ui.chatting.component.BottomChatFieldView
import com.appa.snoop.presentation.ui.chatting.component.MyChatView
import com.appa.snoop.presentation.ui.chatting.component.OtherChatView
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor_70
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.ChattingLaunchedEffect
import com.appa.snoop.presentation.util.extensions.addFocusCleaner
import com.appa.snoop.presentation.util.rememberImeState
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

private const val TAG = "[김희] ChattingScreen_싸피"
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChattingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    chattingViewModel: ChattingViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val lazyState = rememberLazyListState()
    val chatList = chattingViewModel.chatList.value
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }
    val pagingData = chattingViewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        chattingViewModel.getEmailInfo()
        chattingViewModel.setRoomNumber(mainViewModel.chatRoomId)
        Log.d(TAG, "ChattingScreen: 방 번호는? ${mainViewModel.chatRoomId}")
        Log.d(TAG, "ChattingScreen: ${chattingViewModel.email}")
        chattingViewModel.runStomp()
        scope.launch {
            snackState.showSnackbar(
                message = mainViewModel.chatRoomName + " 채팅방에 입장하셨습니다. 자유롭게 정보를 나눠보세요!"
            )
        }
        chattingViewModel.getChatList(mainViewModel.chatRoomId)
    }
//    chattingViewModel.setRoomNumber(4)
//    chattingViewModel.runStomp()

    LaunchedEffect(imeState.value, chattingViewModel.chatList.value, chattingViewModel.chatRecieveState) {
        Log.d(TAG, "ChattingScreen: 업데이트 되었습니다 ${chattingViewModel.chatList.value}")
        if (lazyState.firstVisibleItemIndex == 0) {
            lazyState.animateScrollToItem(0)
        }
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
        snackbarHost = {
            SnackbarHost(snackState)
        },
        floatingActionButton = {
            AnimatedContent(
                targetState = lazyState,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(1000)
                    ) togetherWith fadeOut(
                        animationSpec = tween(1000)
                    ) using SizeTransform(false)
                },
                label = ""
            ) {it ->
                if (it.canScrollBackward) {
                    FloatingActionButton(
                        modifier = Modifier
                            .size(30.sdp),
                        shape = CircleShape,
                        containerColor = WhiteColor,
                        onClick = {
                            scope.launch {
                                lazyState.animateScrollToItem(0)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = null
                        )
                    }
                }
            }
        },
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
                .padding(
                    top = 0.sdp,
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Rtl)
                ),
            verticalArrangement = Arrangement.spacedBy(16.sdp),
            contentPadding = PaddingValues(16.sdp),
            state = lazyState,
            reverseLayout = true
        ) {
            items(
                chattingViewModel.chatList.value,
//                chattingViewModel.chatListState.value
            ) { chat ->
                if (chat.email == chattingViewModel.email) {
                    MyChatView(user = chat)
                } else {
                    OtherChatView(user = chat)
                }
            }
            items(
                pagingData.itemCount
            ) {it ->
                if (pagingData[it]!!.email == chattingViewModel.email) {
                    MyChatView(user = pagingData[it]!!)
                } else {
                    OtherChatView(user = pagingData[it]!!)
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