package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.chat.ChatPaging

interface ChatRepository {
    // 이전 채팅 페이징
    suspend fun getPreChattingList(roomId: Int, page: Int): NetworkResult<ChatPaging>
}