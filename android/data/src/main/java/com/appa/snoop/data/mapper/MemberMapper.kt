package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.member.request.ChangedNicknameRequest
import com.appa.snoop.data.model.member.response.ChangedImageResponse
import com.appa.snoop.data.model.member.response.ChangedNicknameResponse
import com.appa.snoop.data.model.member.response.MemberResponse
import com.appa.snoop.domain.model.member.ChangedImage
import com.appa.snoop.domain.model.member.ChangedNickname
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.model.member.Nickname

fun MemberResponse.toDomain(): Member {
    return Member(
        email = email,
        nickname = nickname,
        profileUrl = profileUrl
    )
}

fun ChangedNicknameResponse.toDomain(): ChangedNickname {
    return ChangedNickname(
        email = email,
        nickname = nickname
    )
}

fun Nickname.toDto(): ChangedNicknameRequest {
    return ChangedNicknameRequest(
        nickName = nickname
    )
}

fun ChangedImageResponse.toDomain(): ChangedImage {
    return ChangedImage(
        profileImageUrl = profileImageUrl
    )
}
