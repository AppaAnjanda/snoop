package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.chat.response.ChatPagingResponse
import com.appa.snoop.data.model.chat.response.ChatResponse
import com.appa.snoop.domain.model.chat.ChatItem
import com.appa.snoop.domain.model.chat.ChatPaging

fun ChatPagingResponse.toDomain() : ChatPaging {
    return ChatPaging(
        contents = contents.map{ it.toDomain() },
        currentPage = currentPage,
        totalPage = totalPage
    )
}

fun ChatResponse.toDomain() : ChatItem {
    return ChatItem(
        roomidx = roomidx,
        email = email,
        sender = sender,
        msg = msg,
        imageUrl = imageUrl,
        time = time
    )
}