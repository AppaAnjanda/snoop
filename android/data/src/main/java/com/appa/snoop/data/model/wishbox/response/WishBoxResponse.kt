package com.appa.snoop.data.model.wishbox.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WishBoxResponse(
    @SerializedName("alertPrice")
    val alertPrice: Int,
    @SerializedName("alertYn")
    val alertYn: Boolean,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productCode")
    val productCode: String,
    @SerializedName("productImage")
    val productImage: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("wishboxId")
    val wishboxId: Int
) : Parcelable