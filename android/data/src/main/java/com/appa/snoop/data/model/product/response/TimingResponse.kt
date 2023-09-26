package com.appa.snoop.data.model.product.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class TimingResponse(
    @SerializedName("avgPrice")
    val avgPrice: Int,
    @SerializedName("curPrice")
    val curPrice: Int,
    @SerializedName("diffPercent")
    val diffPercent: Double,
    @SerializedName("timing")
    val timing: String
) : Parcelable