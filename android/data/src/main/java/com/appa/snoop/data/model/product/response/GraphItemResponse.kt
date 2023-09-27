package com.appa.snoop.data.model.product.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GraphItemResponse(
    @SerializedName("price")
    val price: Int,
    @SerializedName("timestamp")
    val timestamp: String
) : Parcelable