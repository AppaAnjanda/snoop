package com.appa.snoop.data.model.category.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ProductPagingResponse(
    @SerializedName("contents")
    val contents: List<ProductResponse>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
) : Parcelable