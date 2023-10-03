package com.appa.snoop.data.model.notification.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class NotificationResponse(
    @SerializedName("body")
    val body: String,
    @SerializedName("createTime")
    val createTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
) : Parcelable