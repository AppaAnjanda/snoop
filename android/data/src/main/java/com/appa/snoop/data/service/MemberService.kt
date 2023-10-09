package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.member.request.ChangedNicknameRequest
import com.appa.snoop.data.model.member.response.CardListResponse
import com.appa.snoop.data.model.member.response.ChangedImageResponse
import com.appa.snoop.data.model.member.response.ChangedNicknameResponse
import com.appa.snoop.data.model.member.response.MemberResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface MemberService {
    @GET("api/member/info")
    suspend fun getMemberInfo(
    ): MemberResponse

    @PUT("api/member/change")
    suspend fun changeNickname(
        @Body changedNicknameRequest: ChangedNicknameRequest
    ): ChangedNicknameResponse

    @Multipart
    @PUT("api/member/image")
    suspend fun changeImage(
        @Part file: MultipartBody.Part
    ): ChangedImageResponse

    @GET("api/card/myCard")
    suspend fun getMyCard(): CardListResponse

    @GET("api/mypage/recent")
    suspend fun getRecentProduct(): List<ProductResponse>
}