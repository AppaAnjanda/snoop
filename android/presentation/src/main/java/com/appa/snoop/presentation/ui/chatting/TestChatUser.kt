package com.appa.snoop.presentation.ui.chatting

import com.appa.snoop.domain.model.chat.ChatItem

// TODO(테스트 채팅 유저 -> 나중에 서버 DTO 해당하는 Domain Model로 바꿔서 사용)
//data class TestChatUser(
//    val myChat: Boolean = true,
//    val profileUrl: String,
//    val writer: String,
//    val message: String,
//    val time: String
//)

val dummyChat1 = ChatItem(
    roomidx = 4,
    email = "skdi550@nate.com",
    sender = "희웅",
    msg = "테스트 메시지 입니다.",
    imageUrl = "https://github.com/ppeper/ComposePaging/assets/63226023/27c00fa0-40e4-4a82-9e50-14e8996a573e",
    time = "오후 6:32"
)

val dummyChat2 = ChatItem(
    roomidx = 4,
    email = "test1@kakao.com",
    sender = "김진영",
    msg = "ㅋ",
    imageUrl = "https://shopping-phinf.pstatic.net/main_8663600/86636005031.jpg",
    time = "오후 6:32"
)

val dummyChat3 = ChatItem(
    roomidx = 4,
    email = "test2@kakao.com",
    sender = "최영태",
    msg = "테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅",
    imageUrl = "https://shopping-phinf.pstatic.net/main_4098884/40988845618.20230704151344.jpg",
    time = "오후 6:32"
)

val chatList = mutableListOf(
    dummyChat1,
    dummyChat2,
    dummyChat3,
    dummyChat1,
    dummyChat2,
    dummyChat1,
    dummyChat3,
    dummyChat2,
    dummyChat1
)