package com.appa.snoop.data.model.category.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WishToggleResponse(
    @SerializedName("wishYn")
    val wishYn: Boolean
) : Parcelable