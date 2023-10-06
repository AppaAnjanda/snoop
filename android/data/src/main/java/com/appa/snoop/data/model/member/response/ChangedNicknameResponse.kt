package com.appa.snoop.data.model.member.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class ChangedNicknameResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String
) : Parcelable