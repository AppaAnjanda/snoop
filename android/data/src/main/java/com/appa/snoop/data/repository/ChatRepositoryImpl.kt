package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.service.ChatService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.chat.ChatPaging
import com.appa.snoop.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
): ChatRepository {
    override suspend fun getPreChattingList(roomId: Int, page: Int): NetworkResult<ChatPaging> {
        return handleApi { chatService.getPreChattingList(roomId, page).toDomain() }
    }
}