package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone

interface RegisterRepository {
    // 회원가입
    suspend fun registerMember(register: Register): NetworkResult<RegisterDone>

    // 로그인
    suspend fun login(loginInfo: LoginInfo): NetworkResult<JwtTokens>

    // 토큰 값 저장
    suspend fun putJwtTokens(jwtAccessToken: JwtTokens)

    // 로그인 유무 확인
    suspend fun getLoginStatus(): JwtTokens

    // 로그아웃
    suspend fun logout()
}