package com.appa.snoop.domain.model.chat

data class ChatItem(
    val roomidx: Int,
    val email: String?,
    val sender: String,
    val msg: String,
    val imageUrl: String?,
    val time: String
)
