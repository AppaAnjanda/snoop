package com.appa.snoop.presentation.ui.chatting

// TODO(테스트 채팅 유저 -> 나중에 서버 DTO 해당하는 Domain Model로 바꿔서 사용)
data class TestChatUser(
    val myChat: Boolean = true,
    val profileUrl: String,
    val name: String,
    val text: String,
    val time: String
)

val dummyChat1= TestChatUser(
    profileUrl = "https://github.com/ppeper/ComposePaging/assets/63226023/27c00fa0-40e4-4a82-9e50-14e8996a573e",
    name = "김희웅",
    text = "테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅",
    time = "오후 6:32"
)

val dummyChat2 = TestChatUser(
    myChat = false,
    profileUrl = "https://github.com/ppeper/ComposePaging/assets/63226023/83f6af9b-65c9-4cb7-a784-1ea0d8ea31c4",
    name = "김진영",
    text = "테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅",
    time = "오후 6:32"
)

val dummyChat3 = TestChatUser(
    myChat = false,
    profileUrl = "https://github.com/ppeper/ComposePaging/assets/63226023/f5fab2e2-a0b6-471a-bc51-f6d0e69eb39a",
    name = "최영태",
    text = "테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅 테스트 채팅",
    time = "오후 6:32"
)

val chatList = listOf(
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