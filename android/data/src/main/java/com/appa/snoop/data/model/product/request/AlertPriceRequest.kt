package com.appa.snoop.data.model.product.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlertPriceRequest(
    @SerializedName("alertPrice")
    val alertPrice: Int
) : Parcelable
