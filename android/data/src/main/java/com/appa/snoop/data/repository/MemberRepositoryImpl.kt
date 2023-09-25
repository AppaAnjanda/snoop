package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.repository.MemberRepository
import javax.inject.Inject

// TODO domain side Repository 구현
class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRepository {
    override suspend fun getMemberInfo(): NetworkResult<Member> {
        return handleApi {
            memberService.getMemberInfo().toDomain()
        }
    }
}