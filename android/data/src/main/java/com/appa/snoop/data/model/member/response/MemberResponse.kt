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

    @SerializedName("myCardList")
    val myCardList: List<String?>,

    @SerializedName("role")
    val role: String
) : Parcelable
