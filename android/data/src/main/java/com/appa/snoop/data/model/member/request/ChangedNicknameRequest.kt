package com.appa.snoop.data.model.member.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangedNicknameRequest(
    @SerializedName("nickName")
    val nickName: String
) : Parcelable
