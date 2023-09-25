package com.appa.snoop.domain.usecase.member

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Nickname
import com.appa.snoop.domain.repository.MemberRepository
import javax.inject.Inject

class UpdateMemberImageUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(img: String): NetworkResult<ChangedImage> {
        return memberRepository.changeImage(img = img)
    }
}