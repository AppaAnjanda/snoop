package com.appa.snoop.data.model.registration.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginResponse(
    @SerializedName("data")
    val data: AccessTokenResponse,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) : Parcelable