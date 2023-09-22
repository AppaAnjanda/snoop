package com.appa.snoop.data.model.registration.request


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterRequest(
    @SerializedName("cardList")
    val cardList: List<String>,

    @SerializedName("email")
    val email: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("password")
    val password: String
) : Parcelable