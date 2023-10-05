package com.appa.snoop.data.model.chat.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ChatPagingResponse(
    @SerializedName("contents")
    val contents: List<ChatResponse>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
) : Parcelable