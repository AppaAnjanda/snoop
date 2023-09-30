package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.chat.request.ChatRequest
import com.appa.snoop.domain.model.chat.ChatItem

fun ChatRequest.toDomain() : ChatItem {
    return ChatItem(
        roomidx = roomidx,
        email = email,
        sender = sender,
        msg = msg,
        imageUrl = imageUrl,
        time = time
    )
}