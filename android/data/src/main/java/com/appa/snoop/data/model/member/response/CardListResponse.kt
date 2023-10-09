package com.appa.snoop.data.model.member.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CardListResponse(
    @SerializedName("myCardList")
    val myCardList: List<String?>
) : Parcelable