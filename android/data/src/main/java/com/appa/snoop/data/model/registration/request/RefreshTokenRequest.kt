package com.appa.snoop.data.model.registration.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
) : Parcelable