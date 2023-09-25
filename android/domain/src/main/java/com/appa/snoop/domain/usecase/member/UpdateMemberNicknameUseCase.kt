package com.appa.snoop.domain.usecase.member

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Nickname
import com.appa.snoop.domain.repository.MemberRepository
import javax.inject.Inject

class UpdateMemberNicknameUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(nickname: Nickname): NetworkResult<ChangedNickname> {
        return memberRepository.changeNickname(nickname = nickname)
    }
}