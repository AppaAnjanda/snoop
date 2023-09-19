package com.appa.snoop.domain.model.member

data class Register (
    val email: String,
    val nickname: String,
    val password: String,
    val cardList: List<String>
)