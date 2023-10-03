package com.appa.snoop.data.model.wishbox.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WishBoxDeleteListRequest(
    @SerializedName("wishboxIds")
    val wishboxIds: List<Int>
) : Parcelable
