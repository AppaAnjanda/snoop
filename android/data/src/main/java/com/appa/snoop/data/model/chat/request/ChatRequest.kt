package com.appa.snoop.data.model.chat.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRequest(
    @SerializedName("roomidx")
    val roomidx: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("sender")
    val sender: String,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("time")
    val time: String
) : Parcelable