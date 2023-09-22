package com.appa.snoop.data.model.registration.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val data: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)