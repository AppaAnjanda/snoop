package com.appa.snoop.domain.usecase.member

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.model.member.MyCardList
import com.appa.snoop.domain.repository.MemberRepository
import javax.inject.Inject

class GetMyCardListUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): NetworkResult<MyCardList> {
        return memberRepository.getMyCard()
    }
}