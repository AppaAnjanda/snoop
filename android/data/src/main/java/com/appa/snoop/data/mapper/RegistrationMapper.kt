package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.registration.request.RegisterRequest
import com.appa.snoop.data.model.registration.response.AccessTokenResponse
import com.appa.snoop.data.model.registration.response.RegisterResponse
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone

fun RegisterRequest.toDomain(): Register {
    return Register(
        email = email,
        nickname = nickname,
        password = password
    )
}

fun RegisterResponse.toDomain(): RegisterDone {
    return RegisterDone(
        data = data,
        message = message,
        status = status
    )
}

fun AccessTokenResponse.toDomain(): JwtTokens {
    return JwtTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}