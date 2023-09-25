package com.appa.snoop.domain.model.member

data class Member(
    val email: String,
    val nickname: String,
    val cardList: List<String?>,
    val role: String
)
