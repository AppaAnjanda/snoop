package com.appa.snoop.domain.model.chat

data class ChatPaging (
    val contents: List<ChatItem>,
    val currentPage: Int,
    val totalPage: Int
)