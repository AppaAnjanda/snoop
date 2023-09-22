package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.registration.request.RegisterRequest
import com.appa.snoop.data.model.registration.response.LoginResponse
import com.appa.snoop.data.model.registration.response.RegisterResponse
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone

fun RegisterRequest.toDomain(): Register {
    return Register(
        email = email,
        nickname = nickname,
        password = password,
        cardList = cardList
    )
}

fun RegisterResponse.toDomain(): RegisterDone {
    return RegisterDone(
        data = data,
        message = message,
        status = status
    )
}

fun LoginResponse.toDomain(): JwtTokens {
    return JwtTokens(
        accessToken = data.accessToken
    )
}