package com.appa.snoop.data.model.member.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberResponse(
    @SerializedName("data")
    val data: MemberDataResponse,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) : Parcelable
