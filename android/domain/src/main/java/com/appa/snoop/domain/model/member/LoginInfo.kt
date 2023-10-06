package com.appa.snoop.domain.model.member

data class LoginInfo(
    val email: String,
    val fcmToken: String,
    val password: String
)