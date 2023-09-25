package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.Converter
import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.model.member.MyCardList
import com.appa.snoop.domain.model.member.Nickname
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

    override suspend fun changeNickname(nickname: Nickname): NetworkResult<ChangedNickname> {
        return handleApi {
            memberService.changeNickname(nickname.toDto()).toDomain()
        }
    }

    override suspend fun changeImage(img: String): NetworkResult<ChangedImage> {
        val file = Converter.createMultipartBodyPartOnePhoto(img)
        return handleApi {
            memberService.changeImage(file).toDomain()
        }
    }

    override suspend fun getMyCard(): NetworkResult<MyCardList> {
        return handleApi {
            memberService.getMyCard().toDomain()
        }
    }


}