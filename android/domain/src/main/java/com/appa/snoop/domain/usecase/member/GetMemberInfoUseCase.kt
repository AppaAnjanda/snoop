package com.appa.snoop.domain.usecase.member

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.repository.MemberRepository
import javax.inject.Inject

class GetMemberInfoUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): NetworkResult<Member> {
        return memberRepository.getMemberInfo()
    }
}