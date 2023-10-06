package com.appa.snoop.domain.usecase.chatting

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.chat.ChatPaging
import com.appa.snoop.domain.repository.ChatRepository
import javax.inject.Inject

class GetPreChattingListUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(roomId: Int, page: Int): NetworkResult<ChatPaging> {
        return chatRepository.getPreChattingList(roomId, page)
    }
}