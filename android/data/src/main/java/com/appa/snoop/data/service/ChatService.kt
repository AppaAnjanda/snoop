package com.appa.snoop.data.service

import com.appa.snoop.data.model.chat.response.ChatPagingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("api/chat/room/{roomId}/{page}")
    suspend fun getPreChattingList(
        @Path("roomId") roomId: Int,
        @Path("page") page: Int
    ): ChatPagingResponse
}