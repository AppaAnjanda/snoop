package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Member

interface MemberRepository {
    //    suspend fun registerMember(member: Member): NetworkResult<Member>
    suspend fun getMemberInfo(
    ): NetworkResult<Member>
}