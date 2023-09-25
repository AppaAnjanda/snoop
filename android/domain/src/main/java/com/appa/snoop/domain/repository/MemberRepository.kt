package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.model.member.Nickname
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface MemberRepository {
    //    suspend fun registerMember(member: Member): NetworkResult<Member>
    suspend fun getMemberInfo(
    ): NetworkResult<Member>

    suspend fun changeNickname(
        nickname: Nickname
    ): NetworkResult<ChangedNickname>

    @Multipart
    @PUT("api/member/image")
    suspend fun changeImage(
        @Part img: String
    ): NetworkResult<ChangedImage>
}