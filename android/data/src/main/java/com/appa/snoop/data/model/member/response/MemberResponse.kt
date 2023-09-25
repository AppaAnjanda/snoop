package com.appa.snoop.data.model.member.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberResponse(
    @SerializedName("email")
    val email: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("profileUrl")
    val profileUrl: String?,
) : Parcelable
