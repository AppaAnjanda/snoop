package com.appa.snoop.data.model.registration.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AccessTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String
) : Parcelable