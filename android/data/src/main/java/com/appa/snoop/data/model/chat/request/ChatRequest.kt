package com.appa.snoop.data.model.chat.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatResonse(
    @SerializedName("roomidx")
    val roomidx: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("msg")
    val msg: String
) : Parcelable