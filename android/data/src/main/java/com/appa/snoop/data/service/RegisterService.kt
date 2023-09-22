package com.appa.snoop.data.service

import com.appa.snoop.data.model.registration.request.RegisterRequest
import com.appa.snoop.data.model.registration.response.LoginResponse
import com.appa.snoop.data.model.registration.response.RegisterResponse
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.model.member.Register
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("api/member/save")
    suspend fun registerMember(
        @Body register: Register
    ) : RegisterResponse

    @POST("api/member/login")
    suspend fun login(
        @Body loginInfo: LoginInfo
    ) : LoginResponse
}