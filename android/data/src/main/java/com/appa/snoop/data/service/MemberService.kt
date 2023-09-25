package com.appa.snoop.data.service

import com.appa.snoop.data.model.member.response.MemberResponse
import retrofit2.http.GET

interface MemberService {
    @GET("api/member/info")
    suspend fun getMemberInfo(
    ): MemberResponse
}