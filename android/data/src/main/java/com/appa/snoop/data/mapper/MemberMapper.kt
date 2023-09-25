package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.member.response.MemberResponse
import com.appa.snoop.domain.model.member.Member

fun MemberResponse.toDomain(): Member {
    return Member(
        email = email,
        nickname = nickname,
        myCardList = myCardList,
        role = role
    )
}