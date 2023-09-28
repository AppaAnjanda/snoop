package com.appa.snoop.data.service

import com.appa.snoop.data.model.registration.request.RefreshTokenRequest
import com.appa.snoop.data.model.registration.response.AccessTokenResponse
import com.appa.snoop.data.model.registration.response.RegisterResponse
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.model.member.Register
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("api/member/save")
    suspend fun registerMember(
        @Body register: Register
    ) : String

    @POST("api/member/login")
    suspend fun login(
        @Body loginInfo: LoginInfo
    ) : AccessTokenResponse

    @POST("api/member/token")
    suspend fun poseRefreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ) : String
}