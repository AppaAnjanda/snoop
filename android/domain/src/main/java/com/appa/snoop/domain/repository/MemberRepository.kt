package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.model.member.MyCardList
import com.appa.snoop.domain.model.member.Nickname

interface MemberRepository {
    suspend fun getMemberInfo(
    ): NetworkResult<Member>

    suspend fun changeNickname(
        nickname: Nickname
    ): NetworkResult<ChangedNickname>

    suspend fun changeImage(
        file: String
    ): NetworkResult<ChangedImage>

    suspend fun getMyCard(): NetworkResult<MyCardList>
}