package com.appa.snoop.data.model.product.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WishProductResponse(
    @SerializedName("alertPrice")
    val alertPrice: Int,
    @SerializedName("alertYn")
    val alertYn: Boolean,
    @SerializedName("productCode")
    val productCode: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("wishboxId")
    val wishboxId: Int
) : Parcelable