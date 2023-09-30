package com.appa.snoop.data.model.category.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ProductResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("majorCategory")
    val majorCategory: String,
    @SerializedName("minorCategory")
    val minorCategory: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("productLink")
    val productLink: String,
    @SerializedName("productImage")
    val productImage: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("wishYn")
    val wishYn: Boolean,
    @SerializedName("alertYn")
    val alertYn: Boolean
) : Parcelable